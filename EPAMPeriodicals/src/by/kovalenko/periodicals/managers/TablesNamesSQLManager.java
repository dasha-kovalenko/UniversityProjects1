package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public final class TablesNamesSQLManager {
	public static final String EDITIONS = "editions";
	public static final String SUBSCRIPTIONS = "subscriptions";
	public static final String USERS = "users";

	private static TablesNamesSQLManager tablesNamesSQLManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.tableNamesSQL";
	private static Logger log = Logger.getLogger(TablesNamesSQLManager.class);

	public static TablesNamesSQLManager getInstance() {
		if (tablesNamesSQLManager == null)
			tablesNamesSQLManager = new TablesNamesSQLManager(NAME);
		return tablesNamesSQLManager;
	}

	private TablesNamesSQLManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) {
		String returnString = null;
		try {
			returnString = new String(resourceBundle.getString(key).getBytes(),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return returnString;
	}

}
