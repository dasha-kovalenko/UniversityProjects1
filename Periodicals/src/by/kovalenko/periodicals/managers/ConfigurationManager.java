package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class ConfigurationManager {

	public static final String DATABASE_DRIVER_NAME = "databaseDriverName";
	public static final String DATABASE_URL = "databaseUrl";
	public static final String DATABASE_USERNAME = "databaseUsername";
	public static final String DATABASE_PASSWORD = "databasePassword";;
	public static final String EDITION_CREATE_PAGE_PATH = "editionCreatePagePath";
	public static final String EDITION_EDIT_PAGE_PATH = "editionEditPagePath";
	public static final String EDITION_LIST_PAGE_PATH = "editionListPagePath";
	public static final String USER_GET_CART_PAGE_PATH = "userCartPagePath";
	public static final String USER_GET_SUBSCRIPTIONS_PAGE_PATH = "userSubscriptionsPagePath";
	public static final String USER_SUBSCRIPTION_SHOW_PAGE_PATH = "userSubscriptionShowPagePath";
	public static final String USER_LIST_PAGE_PATH = "userListPagePath";
	public static final String USER_EDIT_PAGE_PATH = "userEditPagePath";
	public static final String USER_CREATE_PAGE_PATH = "userCreatePagePath";
	public static final String ERROR_PAGE_PATH = "errorPagePath";

	private static final String NAME = "by.kovalenko.periodicals.properties.configuration";
	private static ConfigurationManager configurationManager;
	private ResourceBundle resourceBundle;

	public static ConfigurationManager getInstance() {
		if (configurationManager == null)
			configurationManager = new ConfigurationManager(NAME);
		return configurationManager;
	}

	private ConfigurationManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}