package by.aig.clientrmi;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientFrameBundle1 {

	private static ClientFrameBundle1 instance;
	public static final String NAME = "by.aig.properties"
			+ File.separator + "messages";

	public static ClientFrameBundle1 getInstance() {
		if (instance == null)
			instance = new ClientFrameBundle1(NAME, Locale.getDefault());
		return instance;
	}

	private ResourceBundle rb;

	private ClientFrameBundle1(String name, Locale locale) {
		rb = ResourceBundle.getBundle(name, locale);
	}

	public void setLocale(Locale locale) {
		rb = ResourceBundle.getBundle(NAME, locale);
	}

	public Locale getLocale() {
		return rb.getLocale();
	}

	public String getString(String key) throws UnsupportedEncodingException {
		return new String(rb.getString(key).getBytes(), "utf-8");
	}

}
