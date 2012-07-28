package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public final class ColumnNamesManager {
	// table editions
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String PRICE = "price";
	public static final String ID = "id";
	// table subscriptions
	public static final String START_DATE = "start_date";
	public static final String FINISH_DATE = "finish_date";
	public static final String PAID = "paid";
	public static final String USER_ID = "user_id";
	// table users
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ADMIN = "admin";
	public static final String SALT = "salt";
	public static final String TOTAL_PRICE = "TotalPrice";

	private static ColumnNamesManager columnNamesManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.columnNames";

	public static ColumnNamesManager getInstance() {
		if (columnNamesManager == null)
			columnNamesManager = new ColumnNamesManager(NAME);
		return columnNamesManager;
	}

	private ColumnNamesManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}
