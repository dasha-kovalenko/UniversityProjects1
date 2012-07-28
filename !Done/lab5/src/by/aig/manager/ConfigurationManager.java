package by.aig.manager;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class ConfigurationManager {

	public static final String DATABASE_DRIVER_NAME = "databaseDriverName";
	public static final String DATABASE_URL = "databaseUrl";
	public static final String DATABASE_USERNAME = "databaseUsername";
	public static final String DATABASE_PASSWORD = "databasePassword";
	public static final String ERROR_PAGE_PATH = "errorPagePath";
	public static final String CANDIDATE_CREATE_PAGE_PATH = "candidateCreatePagePath";
	public static final String CANDIDATE_EDIT_PAGE_PATH = "candidateEditPagePath";
	public static final String CANDIDATE_LIST_PAGE_PATH = "candidateListPagePath";
	public static final String CANDIDATE_SHOW_PAGE_PATH = "candidateShowPagePath";
	
	private static final String NAME = "by.aig.properties.configuration";
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