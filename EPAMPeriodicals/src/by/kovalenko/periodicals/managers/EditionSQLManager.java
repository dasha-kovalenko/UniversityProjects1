package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public final class EditionSQLManager {
	public static final String GET_EDITION = "getEdition";
	public static final String LIST_EDITIONS = "listEditions";
	public static final String DELETE_EDITION = "deleteEdition";
	public static final String SAVE_EDITION = "saveEdition";
	public static final String UPDATE_EDITION = "updateEdition";
	public static final String ADD_EDITION_TO_CART = "addEditionToCart";
	public static final String DELETE_EDITION_FROM_CART = "deleteEditionFromCart";

	private static EditionSQLManager editionSQLManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.editionSQL";

	public static EditionSQLManager getInstance() {
		if (editionSQLManager == null)
			editionSQLManager = new EditionSQLManager(NAME);
		return editionSQLManager;
	}

	private EditionSQLManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}
