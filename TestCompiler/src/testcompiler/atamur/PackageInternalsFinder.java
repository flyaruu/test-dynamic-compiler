package testcompiler.atamur;

import java.io.File;
import java.io.FileNotFoundException;
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
	private final BundleContext bundleContext;
//	private static final String CLASS_FILE_EXTENSION = ".class";

	public PackageInternalsFinder(BundleContext context) {
		this.bundleContext = context;
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
				enumerateWiring(packageName, result, b);
				System.err.println("Result size: "+result.size());
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<JavaFileObject> findAll(String packageName) throws IOException, URISyntaxException {
		
		List<JavaFileObject> result;


		try {
			result = new ArrayList<JavaFileObject>();
			Bundle[] b = bundleContext.getBundles();
			for (Bundle bundle : b) {
				enumerateWiring(packageName, result, bundle);
				System.err.println("Enumerated bundle: "+bundle.getSymbolicName());
			}
			System.err.println("Total for package: "+packageName+": "+result.size());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void enumerateWiring(String packageName, List<JavaFileObject> result, Bundle b) throws URISyntaxException {
		System.err.println("Enumerating for bundle: "+b.getSymbolicName());
		BundleWiring bw =  b.adapt(BundleWiring.class);
		Collection<String> cc = bw.listResources(packageName, null, BundleWiring.LISTRESOURCES_RECURSE);
//		System.err.println("# of resources: " +cc.size()+" - "+packageName);
		for (String string : cc) {
			URL u = b.getResource(string);
			if(u!=null) {
				try {
					final CustomJavaFileObject customJavaFileObject = new CustomJavaFileObject(string, u.toURI(),u.openStream(),Kind.CLASS);
					result.add(customJavaFileObject);
				} catch (FileNotFoundException e) {
//					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


}