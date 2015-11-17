package tz.sys.net.lib;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class URLEncodedUtils {

	private static final char QP_SEP_A = '&';
	private static final char QP_SEP_S = ';';
	private static final String NAME_VALUE_SEPARATOR = "=";

	/**
	 * Query parameter separators.
	 */
	private static final char[] QP_SEPS = new char[] { QP_SEP_A, QP_SEP_S };

	/**
	 * Query parameter separator pattern.
	 */
	private static final String QP_SEP_PATTERN = "[" + new String(QP_SEPS) + "]";
	
	public static List<NVPair> parse(final URI uri) {
		return URLEncodedUtils.parse(uri, "UTF-8");
	}

	 /**
	 * Returns a list of {@link NVPair NameValuePairs} as built from the URI's query portion. For example, a URI
	 * of http://example.org/path/to/file?a=1&b=2&c=3 would return a list of three NameValuePairs, one for a=1, one for
	 * b=2, and one for c=3. By convention, {@code '&'} and {@code ';'} are accepted as parameter separators.
	 * <p>
	 * This is typically useful while parsing an HTTP PUT.
	 *
	 * This API is currently only used for testing.
	 *
	 * @param uri
	 *            URI to parse
	 * @param charset
	 *            Charset name to use while parsing the query
	 * @return a list of {@link NVPair} as built from the URI's query portion.
	 */
	public static List<NVPair> parse(final URI uri, final String charset) {
	    final String query = uri.getRawQuery();
	    if (query != null && query.length() > 0) {
	    	final List<NVPair> result = new ArrayList<NVPair>();
	    	final Scanner scanner = new Scanner(query);
	        parse(result, scanner, QP_SEP_PATTERN, charset);
	        return result;
	    }
	    return Collections.emptyList();
	}


	/**
	 * Adds all parameters within the Scanner to the list of
	 * <code>parameters</code>, as encoded by <code>encoding</code>. For
	 * example, a scanner containing the string <code>a=1&b=2&c=3</code> would
	 * add the {@link NVPair NameValuePairs} a=1, b=2, and c=3 to the
	 * list of parameters.
	 *
	 * @param parameters
	 *            List to add parameters to.
	 * @param scanner
	 *            Input that contains the parameters to parse.
	 * @param parameterSepartorPattern
	 *            The Pattern string for parameter separators, by convention {@code "[&;]"}
	 * @param charset
	 *            Encoding to use when decoding the parameters.
	 */
	public static void parse(final List <NVPair> parameters, final Scanner scanner, final String parameterSepartorPattern, final String charset) {
		scanner.useDelimiter(parameterSepartorPattern);
		while (scanner.hasNext()) {
	        String name = null;
	        String value = null;
	        final String token = scanner.next();
	        final int i = token.indexOf(NAME_VALUE_SEPARATOR);
	        if (i != -1) {
	            name = decodeFormFields(token.substring(0, i).trim(), charset);
	            value = decodeFormFields(token.substring(i + 1).trim(), charset);
	        } else {
	            name = decodeFormFields(token.trim(), charset);
	        }
	        parameters.add(new NVPair(name, value));
	    }
	}


	/**
	 * Decode/unescape www-url-form-encoded content.
	 *
	 * @param content the content to decode, will decode '+' as space
	 * @param charset the charset to use
	 * @return encoded string
	 */
	private static String decodeFormFields (final String content, final String charset) {
	    if (content == null) {
	        return null;
	    }
	    return urlDecode(content, charset != null ? Charset.forName(charset) : Charset.forName("UTF-8"), true);
	}

	/**
	 * Decode/unescape a portion of a URL, to use with the query part ensure {@code plusAsBlank} is true.
	 *
	 * @param content the portion to decode
	 * @param charset the charset to use
	 * @param plusAsBlank if {@code true}, then convert '+' to space (e.g. for www-url-form-encoded content), otherwise leave as is.
	 * @return encoded string
	 */
	private static String urlDecode(final String content, final Charset charset, final boolean plusAsBlank) {
	    if (content == null) {
	        return null;
	    }
	    final ByteBuffer bb = ByteBuffer.allocate(content.length());
	    final CharBuffer cb = CharBuffer.wrap(content);
	    while (cb.hasRemaining()) {
	        final char c = cb.get();
	        if (c == '%' && cb.remaining() >= 2) {
	            final char uc = cb.get();
	            final char lc = cb.get();
	            final int u = Character.digit(uc, 16);
	            final int l = Character.digit(lc, 16);
	            if (u != -1 && l != -1) {
	                bb.put((byte) ((u << 4) + l));
	            } else {
	                bb.put((byte) '%');
	                bb.put((byte) uc);
	                bb.put((byte) lc);
	            }
	        } else if (plusAsBlank && c == '+') {
	            bb.put((byte) ' ');
	        } else {
	            bb.put((byte) c);
	        }
	    }
	    bb.flip();
	    return charset.decode(bb).toString();
	}
	
	public static class NVPair implements CharSequence {

	    private String value;
	    private String name;

	    public NVPair(String name, String value) {
	        this.value = value;
	        this.name = name;
	    }

	    public String value() {
	        return this.value;
	    }

	    public NVPair value(String value) {
	        this.value = value;
	        return this;
	    }

	    public String name() {
	        return this.name;
	    }

	    public NVPair name(String name) {
	        this.name = name;
	        return this;
	    }
	    
	    @Override
	    public String toString() {
	    	return this.name + "=" + this.value;
	    }

		@Override
		public char charAt(int index) {
			return this.toString().charAt(index);
		}

		@Override
		public int length() {
			return this.toString().length();
		}

		@Override
		public CharSequence subSequence(int beginIndex, int endIndex) {
			return this.toString().subSequence(beginIndex, endIndex);
		}
	}
	
}
