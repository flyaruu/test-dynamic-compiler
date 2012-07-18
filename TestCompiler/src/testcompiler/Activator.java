package testcompiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

import testcompiler.atamur.CustomClassloaderJavaFileManager;
import testcompiler.atamur.CustomJavaFileObject;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private StandardJavaFileManager fileManager;
	private CustomClassloaderJavaFileManager customJavaFileManager;
	private JavaCompiler compiler;
	private DiagnosticListener<JavaFileObject> compilerOutputListener;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */

	public void starta(BundleContext bundleContext) throws Exception {
		Bundle[] b = bundleContext.getBundles();
		for (Bundle bundle : b) {
			if(bundle.getSymbolicName().startsWith("com.dexels.navajo.document")) {
				BundleWiring bw = bundle.adapt(BundleWiring.class);
				System.err.println("wiring found");
				Collection<String> cc = bw.listResources("com/dexels/navajo/document", null, BundleWiring.LISTRESOURCES_RECURSE);
				for (String resource : cc) {
					URL u = bundle.getResource(resource);
					System.err.println("resource: "+resource+" = "+u);
				}
			}
		}
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		final File test_src     = new File("javasrc");
		
		compiler = ToolProvider.getSystemJavaCompiler();
		compilerOutputListener = new DiagnosticListener<JavaFileObject>() {

			@Override
			public void report(Diagnostic<? extends JavaFileObject> jfo) {

				System.err.println("PROBLEM: "+jfo.getMessage(Locale.ENGLISH));
			}
			
		};
		fileManager = compiler.getStandardFileManager(compilerOutputListener, null, null);
		customJavaFileManager = new CustomClassloaderJavaFileManager(context, getClass().getClassLoader(), fileManager);

		compile(getJavaSourceFileObject("mathtest/Calculator", getExampleCode()));
	}

	private void compile(JavaFileObject javaSource) throws IOException {
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(javaSource);

		CompilationTask task = compiler.getTask(null, customJavaFileManager, compilerOutputListener,new ArrayList<String>(), null, fileObjects);
		task.call();
		CustomJavaFileObject jfo = (CustomJavaFileObject) customJavaFileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, "mathtest.Calculator", Kind.CLASS);
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
		customJavaFileManager.close();
		Activator.context = null;
	}

	
	private InputStream getExampleCode() {
        String example = 									
        		"package mathtest;\n"+
                "public class Calculator { \n"
               + "  public void testAdd() { "
               + "    System.out.println(200+300); \n"
               + "    com.dexels.navajo.document.Navajo aaaa; \n"
//               + "   testcompiler.Activator a = new testcompiler.Activator();} \n"
             + "   } \n"
               + "  public static void main(String[] args) { \n"
               + "    Calculator cal = new Calculator(); \n"
               + "    cal.testAdd(); \n"
               + "  } " + "} ";	
        return new ByteArrayInputStream(example.getBytes());
	}
	
    private  JavaFileObject getJavaSourceFileObject(String className, InputStream contents)
    {

        JavaFileObject so = null;
        try
        {
//			so = new InMemoryJavaFileObject(className, contents.toString());
            so = new CustomJavaFileObject(className+ Kind.SOURCE.extension, URI.create("file:///" + className.replace('.', '/')
                    + Kind.SOURCE.extension), contents, Kind.SOURCE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return so;
    }
 
}
