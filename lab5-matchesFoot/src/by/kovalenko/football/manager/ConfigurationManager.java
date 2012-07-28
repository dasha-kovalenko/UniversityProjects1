package by.kovalenko.football.manager;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class ConfigurationManager {

	public static final String DATABASE_DRIVER_NAME = "databaseDriverName";
	public static final String DATABASE_URL = "databaseUrl";
	public static final String DATABASE_USERNAME = "databaseUsername";
	public static final String DATABASE_PASSWORD = "databasePassword";
	public static final String ERROR_PAGE_PATH = "errorPagePath";
	public static final String MATCH_CREATE_PAGE_PATH = "matchCreatePagePath";
	public static final String MATCH_EDIT_PAGE_PATH = "matchEditPagePath";
	public static final String MATCH_LIST_PAGE_PATH = "matchListPagePath";
	public static final String MATCH_SHOW_PAGE_PATH = "matchShowPagePath";
	
	private static final String NAME = "by.kovalenko.football.properties.configuration";
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