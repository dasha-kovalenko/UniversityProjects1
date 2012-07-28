package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public final class LocaleManager {

	public static final String INCORRECT_USER_ATTRIBUTES = "incorrectUserAttributes";
	public static final String ACCESS_IS_DENIED = "accessIsDenied";
	public static final String EDITION_IS_ADDED_TO_CART = "editionIsAddedToCart";

	private static final String NAME = "by.kovalenko.periodicals.properties.locale";
	private static LocaleManager localeManager;
	private ResourceBundle resourceBundle;

	public static LocaleManager getInstance() {
		if (localeManager == null)
			localeManager = new LocaleManager(NAME);
		return localeManager;
	}

	private LocaleManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	private LocaleManager(String name, Locale locale) {
		resourceBundle = ResourceBundle.getBundle(name, locale);
	}

	public void setLocale(String locale) {
		localeManager = new LocaleManager(NAME, new Locale(locale));
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}
