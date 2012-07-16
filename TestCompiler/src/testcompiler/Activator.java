package testcompiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.commons.io.IOUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import testcompiler.atamur.CustomClassloaderJavaFileManager;
import testcompiler.atamur.CustomJavaFileObject;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
//		Compile.compile();
//		TslCompiler.main(new String[]{"tslsrc","testtsl","-all"});
//		TslCompiler.compileDirectory(new File("tslsrc"), new File("testtsl"), "",null,"/Users/frank/Documents/workspace42/sportlink-serv/navajo-tester/auxilary/config");

//		Bundle[] b = context.getBundles();
//		for (Bundle bundle : b) {
//			System.err.println(">> "+bundle.getLocation());
//		}
//		
		
//		List<String> options = new ArrayList<String>();
//		options.add("-classpath");
//		StringBuilder sb = new StringBuilder();
//		URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
//		for (URL url : urlClassLoader.getURLs()) {
//		        sb.append(url.getFile()).append(File.pathSeparator);
//		        System.err.println(url.getFile() + ";");
//		}
//		options.add(sb.toString());
		
		
		final File test_src     = new File("javasrc");
		
		
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String[] options = {"-classpath", System.getProperty("java.class.path")};
		DiagnosticListener<JavaFileObject> listener = new DiagnosticListener<JavaFileObject>() {

			@Override
			public void report(Diagnostic<? extends JavaFileObject> jfo) {
				System.err.println("PROBLEMO "+jfo.getMessage(Locale.ENGLISH));
			}
			
		};
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(listener, null, null);

		
//		InputStream is = getClass().getResourceAsStream("Activator.class");
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		IOUtils.copy(is, baos);
//		System.err.println("> "+new String(baos.toByteArray()));

		JavaFileManager fileManager2 = new ForwardingJavaFileManager<JavaFileManager>(fileManager){

			
			@Override
			public boolean handleOption(String current,
					Iterator<String> remaining) {
				System.err.println("handle: "+current);
				return super.handleOption(current, remaining);
			}

			@Override
			public boolean isSameFile(FileObject a, FileObject b) {
				System.err.println("same");
				return super.isSameFile(a, b);
			}

			@Override
			public int isSupportedOption(String option) {
				System.err.println("support: "+option);
				return super.isSupportedOption(option);
			}

			@Override
			public String inferBinaryName(Location location, JavaFileObject file) {
				System.err.println("Inferring: "+file+" location: "+location);
				return super.inferBinaryName(location, file);
			}

			@Override
			public Iterable<JavaFileObject> list(Location location,
					String packageName, Set<Kind> kinds, boolean recurse)
					throws IOException {
				System.err.println("listing: "+location+" pachage: "+packageName );
				
				Iterable<JavaFileObject> parentList = super.list(location, packageName, kinds, recurse);

				List<JavaFileObject> mine = new ArrayList<JavaFileObject>();
				for (JavaFileObject fj : parentList) {
					mine.add(fj);
				}
				if("testcompiler".equals(packageName)) {
					try {
//						final InputStream resourceAsStream = Activator.this.getClass().getClassLoader().getResourceAsStream("testcompiler.Activator.class");

						final InputStream resourceAsStream = Activator.this.getClass().getClassLoader().getResourceAsStream("testcompiler/Activator.class");
						Class s = Class.forName("testcompiler.Activator", true,  Activator.this.getClass().getClassLoader());
						System.err.println("s: "+s);
						mine.add(new InMemoryBinaryFileObject("testcompiler.Activator", resourceAsStream));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.err.println("Parent list: "+mine);
//				mine.add(getJavaFileObject());
				return mine;
			}

			@Override
			public FileObject getFileForInput(Location location,
					String packageName, String relativeName) throws IOException {
				System.err.println("java Location: "+location.getName());
				return super.getFileForInput(location, packageName, relativeName);
			}

			@Override
			public JavaFileObject getJavaFileForInput(Location location,
					String className, Kind kind) throws IOException {
				System.err.println("java Location: "+location.getName()+" cl: "+className);
				return super.getJavaFileForInput(location, className, kind);
			}

			@Override
			public boolean hasLocation(Location location) {
				final boolean hasLocation = super.hasLocation(location);
				System.err.println("hasLocation: "+location.getName()+" s: "+location.toString()+" : "+hasLocation);
				return hasLocation;
			}

			@Override
			public FileObject getFileForOutput(Location location,
					String packageName, String relativeName, FileObject sibling)
					throws IOException {
				System.err.println("out: "+location+" rel: "+relativeName);
				return super.getFileForOutput(location, packageName, relativeName, sibling);
			}

			@Override
			public JavaFileObject getJavaFileForOutput(Location location,
					String className, Kind kind, FileObject sibling)
					throws IOException {
				System.err.println("out java: "+location);
				return super.getJavaFileForOutput(location, className, kind, sibling);
			}

			@Override
			public ClassLoader getClassLoader(Location location) {
				System.err.println("getClassLoader: "+location);
				return Activator.this.getClass().getClassLoader();
//				return super.getClassLoader(location);
			}
			
			
			
			
		};
		
		fileManager.setLocation(StandardLocation.SOURCE_PATH, Collections.singleton(test_src));
		
//		Iterable<? extends JavaFileObject> fileObjects = fileManager2.

//		Iterable<? extends JavaFileObject> fileObjects = fileManager2.getFileForInput(new Location(), sourceFile, "club", "InitUpdateClub");
//		File []files = new File[]{new File("javasrc/club/InitUpdateClub.java")} ;
		 Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(getJavaFileObject());
		 
//		Iterable<? extends JavaFileObject> fileObjects =
//		           fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files));

		CustomClassloaderJavaFileManager ccjfm = new CustomClassloaderJavaFileManager(context, getClass().getClassLoader(), fileManager);
		CompilationTask task = compiler.getTask(null, ccjfm, listener, Arrays.asList(options), null, fileObjects);
		task.call();
		CustomJavaFileObject jfo = (CustomJavaFileObject) ccjfm.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, "mathtest.Calculator", Kind.CLASS);
		if (jfo==null) {
			System.err.println("compile failed?");
		} else {
			System.err.println("jfo: "+jfo.getSize());
		}
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	
    private  JavaFileObject getJavaFileObject()
    {
        StringBuilder contents = new StringBuilder(
                                                   "package mathtest;\n"+
                                                            "public class Calculator { \n"
                                                           + "  public void testAdd() { "
                                                           + "    System.out.println(200+300); \n"
                                                           + "    com.dexels.navajo.document.Navajo aaaa; \n"
                                                           + "   testcompiler.Activator a = new testcompiler.Activator();} \n"
                                                           + "  public static void main(String[] args) { \n"
                                                           + "    Calculator cal = new Calculator(); \n"
                                                           + "    cal.testAdd(); \n"
                                                           + "  } " + "} ");
        JavaFileObject so = null;
        try
        {
            so = new InMemoryJavaFileObject("mathtest.Calculator", contents.toString());
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return so;
    }
	
    public static class InMemoryJavaFileObject extends SimpleJavaFileObject
    {
        private String contents = null;
 
        public InMemoryJavaFileObject(String className, String contents) throws Exception
        {
            super(URI.create("string:///" + className.replace('.', '/')
                             + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }
 
        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException
        {
            return contents;
        }
    }
 
    public static class InMemoryBinaryFileObject extends SimpleJavaFileObject
    {
        private  byte[] contents = null;
        
//    	private final ByteArrayOutputStream bais;
    	
        public InMemoryBinaryFileObject(String className, InputStream input) throws Exception
        {
            super(URI.create("string:///" + className.replace('.', '/')
                             + Kind.CLASS.extension), Kind.CLASS);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            this.contents = contents;
            IOUtils.copy(input, baos);
            this.contents = baos.toByteArray();
        }
 
		@Override
		public Kind getKind() {
			return Kind.CLASS;
		}

		@Override
		public InputStream openInputStream() throws IOException {

			return new ByteArrayInputStream(contents);
		}

//		@Override
//		public OutputStream openOutputStream() throws IOException {
//			return super.openOutputStream();
//		}
        
        
    }
 
}
