package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public final class CommandManager {
	public static final String COMMAND = "command";
	public static final String EDITION_CREATE = "editionCreate";
	public static final String EDITION_EDIT = "editionEdit";
	public static final String EDITION_DELETE = "editionDelete";
	public static final String EDITION_LIST = "editionList";
	public static final String EDITION_SAVE = "editionSave";
	public static final String EDITION_UPDATE = "editionUpdate";
	public static final String EDITION_ADD_TO_CART = "editionAddToCart";
	public static final String EDITION_DELETE_FROM_CART = "editionDeleteFromCart";
	public static final String USER_ADD_CART_TO_SUBSCRIPTIONS = "userAddCartToSubscriptions";
	public static final String USER_GET_CART = "userGetCart";
	public static final String USER_GET_SUBSCRUPTIONS = "userGetSubscriptions";
	public static final String USER_LIST = "userList";
	public static final String USER_DELETE = "userDelete";
	public static final String USER_SUBSCRIPTION_PAY = "userSubscriptionPay";
	public static final String USER_SUBSCRIPTION_SHOW = "userSubscriptionShow";
	public static final String USER_SUBSCRIPTION_DELETE = "userSubscriptionDelete";
	public static final String USER_SUBSCRIPTION_CREATE = "userSubscriptionCreate";
	public static final String USER_UPDATE = "userUpdate";
	public static final String USER_EDIT = "userEdit";
	public static final String USER_CREATE = "userCreate";
	public static final String USER_LOGIN = "userLogIn";
	public static final String USER_LOGOUT = "userLogOut";
	public static final String ERROR = "error";
	
	private static CommandManager commandManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.commands";

	public static CommandManager getInstance() {
		if (commandManager == null)
			commandManager = new CommandManager(NAME);
		return commandManager;
	}

	private CommandManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}
}
