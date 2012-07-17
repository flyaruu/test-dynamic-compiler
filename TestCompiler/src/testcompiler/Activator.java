package testcompiler;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

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
//			BundleWiring bw = bundle.adapt(BundleWiring.class);
//			System.err.println("bundlew: "+bw.toString());
//		}
		
		
//		List<String> options = new ArrayList<String>();
//		options.add("-classpath");
//		StringBuilder sb = new StringBuilder();
//		URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
//		for (URL url : urlClassLoader.getURLs()) {
//		        sb.append(url.getFile()).append(File.pathSeparator);
//		        System.err.println(url.getFile() + ";");
//		}
//		options.add(sb.toString());
		
//		org.eclipse.jdt.internal.compiler.tool.EclipseCompiler
		
		final File test_src     = new File("javasrc");
		
//		JavaCompiler compiler = new org.eclipse.jdt.internal.compiler.tool.EclipseCompiler();
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String[] options = {"-classpath", System.getProperty("java.class.path")};
		DiagnosticListener<JavaFileObject> listener = new DiagnosticListener<JavaFileObject>() {

			@Override
			public void report(Diagnostic<? extends JavaFileObject> jfo) {
				System.err.println("PROBLEMO "+jfo.getMessage(Locale.ENGLISH));
			}
			
		};
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(listener, null, null);

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
//                                                           + "    com.dexels.navajo.document.Navajo aaaa; \n"
//                                                           + "   testcompiler.Activator a = new testcompiler.Activator();} \n"
                                                         + "   } \n"
                                                           + "  public static void main(String[] args) { \n"
                                                           + "    Calculator cal = new Calculator(); \n"
                                                           + "    cal.testAdd(); \n"
                                                           + "  } " + "} ");
        JavaFileObject so = null;
        try
        {
            final String className = "mathtest/Calculator";
//			so = new InMemoryJavaFileObject(className, contents.toString());
            so = new CustomJavaFileObject(className, URI.create("file:///" + className.replace('.', '/')
                    + Kind.SOURCE.extension), Kind.SOURCE, contents.toString());
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return so;
    }
 
}
