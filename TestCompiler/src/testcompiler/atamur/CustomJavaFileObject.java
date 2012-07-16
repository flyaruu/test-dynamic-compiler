package testcompiler.atamur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;

/**
 * @author atamur
 * @since 15-Oct-2009
 */
public class CustomJavaFileObject implements JavaFileObject {
	private final String binaryName;
	private final URI uri;
	private final String name;
	private ByteArrayOutputStream baos;

	public CustomJavaFileObject(String binaryName, URI uri, InputStream is) {
		this.uri = uri;
		this.binaryName = binaryName;
		String stripName = binaryName;
		if(stripName.endsWith("/")) {
			stripName = stripName.substring(0,stripName.length()-1);
		}
		name = binaryName; //.substring(binaryName.lastIndexOf('/')+1);
		if(is!=null) {
			baos = new ByteArrayOutputStream();
			try {
				IOUtils.copy(is, baos);
				System.err.println("Bin: "+binaryName+"  >>>> file size: "+baos.toByteArray().length);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public URI toUri() {
		return uri;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		
		final byte[] byteArray = baos.toByteArray();
		System.err.println("#Bytes "+byteArray.length);
		return new ByteArrayInputStream(byteArray); // easy way to handle any URI!
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		baos = new ByteArrayOutputStream();
		return baos;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Writer openWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLastModified() {
		return 0;
	}

	@Override
	public boolean delete() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Kind getKind() {
		return Kind.CLASS;
	}

	@Override
	// copied from SImpleJavaFileManager
	public boolean isNameCompatible(String simpleName, Kind kind) {
		String baseName = simpleName + kind.extension;
		return kind.equals(getKind())
				&& (baseName.equals(getName()) || getName().endsWith(
						"/" + baseName));
	}

	@Override
	public NestingKind getNestingKind() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Modifier getAccessLevel() {
		throw new UnsupportedOperationException();
	}

	public String binaryName() {
		return binaryName;
	}

	@Override
	public String toString() {
		return "CustomJavaFileObject{" + "uri=" + uri + '}';
	}

	public int getSize() {
		if (baos==null) {
			return -1;
		} else {
			return baos.size();
		}
		
	}
}