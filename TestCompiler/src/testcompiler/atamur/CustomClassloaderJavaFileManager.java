package testcompiler.atamur;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.osgi.framework.BundleContext;

/**
 * @author atamur
 * @since 15-Oct-2009
 */
public class CustomClassloaderJavaFileManager extends
		ForwardingJavaFileManager<JavaFileManager> implements JavaFileManager {
	private final ClassLoader classLoader;
	private final JavaFileManager standardFileManager;
	private final PackageInternalsFinder finder;

	private final Map<String, CustomJavaFileObject> fileMap = new HashMap<String, CustomJavaFileObject>();

	public CustomClassloaderJavaFileManager(BundleContext context,
			ClassLoader classLoader, JavaFileManager standardFileManager) {
		super(standardFileManager);
		this.classLoader = classLoader;
		this.standardFileManager = standardFileManager;
		finder = new PackageInternalsFinder(context);
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		return classLoader;
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		if (file instanceof CustomJavaFileObject) {
			final String binaryName = ((CustomJavaFileObject) file).binaryName();
			String stripped = binaryName.substring(
					binaryName.lastIndexOf("/") + 1, binaryName.indexOf('.'));
			return stripped;
		} else { // if it's not CustomJavaFileObject, then it's coming from
					// standard file manager - let it handle the file
			return standardFileManager.inferBinaryName(location, file);
		}
	}

	// @Override
	// public boolean isSameFile(FileObject a, FileObject b) {
	// throw new UnsupportedOperationException();
	// }
	//
	// @Override
	// public boolean handleOption(String current, Iterator<String> remaining) {
	// throw new UnsupportedOperationException();
	// }
	//
	@Override
	public boolean hasLocation(Location location) {
		final boolean b = location == StandardLocation.CLASS_PATH
				|| location == StandardLocation.PLATFORM_CLASS_PATH;
		System.err.println("Has: " + location + "? " + location);
		return b;
	}

	 @Override
	 public JavaFileObject getJavaFileForInput(Location location, String
	 className, JavaFileObject.Kind kind) throws IOException {
			String binaryName = className.replaceAll("\\.", "/");
		 if(kind.equals(Kind.CLASS)) {
			 binaryName = binaryName + ".class";
		 } else {
			 binaryName = binaryName+".java";
		 }
			CustomJavaFileObject cjfo =  fileMap.get(binaryName); 
			if(cjfo==null) {
				System.err.println("File not found? keys: "+fileMap.keySet());
			}
			return cjfo;
	 }
	
	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String className, JavaFileObject.Kind kind, FileObject sibling)
			throws IOException {
		// throw new UnsupportedOperationException();
		String binaryName = className.replaceAll("\\.","/" ) + kind.extension;
		URI uri = URI.create("file:///" + binaryName);
		CustomJavaFileObject cjfo =  fileMap.get(binaryName); //new CustomJavaFileObject(binaryName, uri,fileMap.get(uri.toString()));
		if(cjfo==null) {
			System.err.println("filemap: "+fileMap);
			System.err.println("bin: "+binaryName);
			cjfo = new CustomJavaFileObject(binaryName, uri,(InputStream)null,kind);
			fileMap.put(binaryName, cjfo);
			
		}
		return cjfo;
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName,
			String relativeName) throws IOException {
		System.err.println("Hetting location: " + location.getName()
				+ " pack: " + packageName + " rel: " + relativeName);
		JavaFileObject jf = fileMap.get(location.getName());
		if (jf != null) {
			return jf;
		}
		return super.getFileForInput(location, packageName, relativeName);
		// throw new UnsupportedOperationException();
	}

	// @Override
	// public FileObject getFileForOutput(Location location, String packageName,
	// String relativeName, FileObject sibling) throws IOException {
	// throw new UnsupportedOperationException();
	// }

	// @Override
	// public void flush() throws IOException {
	// // do nothing
	// }
	//
	// @Override
	// public void close() throws IOException {
	// // do nothing
	// }

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName,
			Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
		System.err.println("AAAAAAAAAA\nAAAAAAAAAA");
		if (location == StandardLocation.PLATFORM_CLASS_PATH) { // let standard
																// manager
																// hanfle
			return standardFileManager.list(location, packageName, kinds,
					recurse);
		} else if (location == StandardLocation.CLASS_PATH
				&& kinds.contains(JavaFileObject.Kind.CLASS)) {
			if (packageName.startsWith("java")) { // a hack to let standard
													// manager handle locations
													// like "java.lang" or
													// "java.util". Prob would
													// make sense to join
													// results of standard
													// manager with those of my
													// finder here
				return standardFileManager.list(location, packageName, kinds,
						recurse);
			} else { // app specific classes are here
				try {
//					final List<JavaFileObject> find = finder.find(packageName,kinds, recurse);
					final List<JavaFileObject> find = finder.findAll(packageName);
					for (JavaFileObject javaFileObject : find) {
						fileMap.put(((CustomJavaFileObject) javaFileObject)
								.binaryName(),
								(CustomJavaFileObject) javaFileObject);
					}
					return find;
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		return Collections.emptyList();

	}

	@Override
	public int isSupportedOption(String option) {
		return -1;
	}

}