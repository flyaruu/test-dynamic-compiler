package testcompiler.atamur;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author atamur
 * @since 15-Oct-2009
 */
public class PackageInternalsFinder {
//	private ClassLoader classLoader;
//	private final BundleContext bundleContext;
//	private static final String CLASS_FILE_EXTENSION = ".class";

	public PackageInternalsFinder() {
//		this.classLoader = classLoader;
//		this.bundleContext = context;
	}

	public List<JavaFileObject> find(String packageName, Set<Kind> kinds, boolean recurse) throws IOException, URISyntaxException {
		
		List<JavaFileObject> result;
		try {
			ClassLoader cl = this.getClass().getClassLoader();
			result = new ArrayList<JavaFileObject>();
			if (cl instanceof BundleReference) {
				System.err.println("<<<<<<<<<<<<<<<<<<<<< ninflr" );
				BundleReference ref = (BundleReference) cl;
				Bundle b = ref.getBundle();
				BundleWiring bw =  b.adapt(BundleWiring.class);
				Collection<String> cc = bw.listResources(packageName, null, BundleWiring.LISTRESOURCES_RECURSE);
				System.err.println("# of resources: " +cc.size());
				for (String string : cc) {
					System.err.println("Looking for: "+packageName+ " Found: "+string);
					URL u = b.getResource(string);
					System.err.println(string+" u: "+u.toURI());
					try {
						final CustomJavaFileObject customJavaFileObject = new CustomJavaFileObject(string, u.toURI(),u.openStream());
						System.err.println("Name: "+customJavaFileObject.getName()+" kinds: "+kinds+" recurse: "+recurse);
						result.add(customJavaFileObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.err.println("Result size: "+result.size());
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		//		String javaPackageName = packageName.replaceAll("\\.", "/");
//
//
//		// Enumeration<URL> urlEnumeration =
//		// classLoader.getResources(javaPackageName);
//		Enumeration<URL> urlEnumeration = bundleContext.getBundle()
//				.getResources(javaPackageName);
//		if (urlEnumeration != null) {
//			while (urlEnumeration.hasMoreElements()) { // one URL for each jar
//														// on the classpath that
//														// has the given package
//				URL packageFolderURL = urlEnumeration.nextElement();
//				result.addAll(listUnder(packageName, packageFolderURL));
//			}
//		}
//		return result;
	}

//	private Collection<JavaFileObject> listUnder(String packageName,
//			URL packageFolderURL) {
//		File directory = new File(packageFolderURL.getFile());
//		if (directory.isDirectory()) { // browse local .class files - useful for
//										// local execution
//			return processDir(packageName, directory);
//		} else { // browse a jar file
//			return processJar(packageFolderURL);
//		} // maybe there can be something else for more involved class loaders
//	}

//	private List<JavaFileObject> processJar(URL packageFolderURL) {
//		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
//		// bundleresource://32.fwk363211825/testcompiler/
//		try {
//			String jarUri = packageFolderURL.toExternalForm().split("!")[0];
//			JarURLConnection jarConn = (JarURLConnection) packageFolderURL
//					.openConnection();
//			String rootEntryName = jarConn.getEntryName();
//			int rootEnd = rootEntryName.length() + 1;
//
//			Enumeration<JarEntry> entryEnum = jarConn.getJarFile().entries();
//			while (entryEnum.hasMoreElements()) {
//				JarEntry jarEntry = entryEnum.nextElement();
//				String name = jarEntry.getName();
//				if (name.startsWith(rootEntryName)
//						&& name.indexOf('/', rootEnd) == -1
//						&& name.endsWith(CLASS_FILE_EXTENSION)) {
//					URI uri = URI.create(jarUri + "!/" + name);
//					String binaryName = name.replaceAll("/", ".");
//					binaryName = binaryName.replaceAll(CLASS_FILE_EXTENSION
//							+ "$", "");
//
//					result.add(new CustomJavaFileObject(binaryName, uri));
//				}
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("Wasn't able to open "
//					+ packageFolderURL + " as a jar file", e);
//		}
//		return result;
//	}
//
//	private List<JavaFileObject> processDir(String packageName, File directory) {
//		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
//
//		File[] childFiles = directory.listFiles();
//		for (File childFile : childFiles) {
//			if (childFile.isFile()) {
//				// We only want the .class files.
//				if (childFile.getName().endsWith(CLASS_FILE_EXTENSION)) {
//					String binaryName = packageName + "." + childFile.getName();
//					binaryName = binaryName.replaceAll(CLASS_FILE_EXTENSION
//							+ "$", "");
//
//					result.add(new CustomJavaFileObject(binaryName, childFile
//							.toURI()));
//				}
//			}
//		}
//
//		return result;
//	}
}