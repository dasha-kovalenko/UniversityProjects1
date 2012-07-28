package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PathToRedirectManager {
	public static final String EDITION_ADD_TO_CART = "editionAddToCart";
	public static final String EDITION_DELETE = "editionDelete";
	public static final String EDITION_DELETE_FROM_CART = "editionDeleteFromCart";
	public static final String EDITION_SAVE = "editionSave";
	public static final String USER_ADD_CART_TO_SUBSCRIPTIONS = "userAddCartToSubscriptions";
	public static final String USER_DELETE = "userDelete";
	public static final String USER_UPDATE = "userUpdate";
	public static final String USER_EDIT = "userEdit";
	public static final String USER_CREATE = "userCreate";
	public static final String USER_LOGIN = "userLogIn";
	public static final String USER_LOGOUT = "userLogOut";
	public static final String ERROR = "error";

	private static PathToRedirectManager pathToRedirectManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.pathToRedirect";
	private static Logger log = Logger.getLogger(TablesNamesSQLManager.class);

	public static PathToRedirectManager getInstance() {
		if (pathToRedirectManager == null)
			pathToRedirectManager = new PathToRedirectManager(NAME);
		return pathToRedirectManager;
	}

	private PathToRedirectManager(String name) {
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
