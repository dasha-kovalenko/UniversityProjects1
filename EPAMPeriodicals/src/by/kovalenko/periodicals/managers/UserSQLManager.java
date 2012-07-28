package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public final class UserSQLManager {
	public static final String GET_EDITIONS = "getEditions";
	public static final String GET_CART = "getCart";
	public static final String GET_USER_WITH_ID = "getUserWithId";
	public static final String LIST_USERS = "listUsers";
	public static final String DELETE_USER_FROM_CARTS = "deleteUserFromCarts";
	public static final String DELETE_USER_FROM_SUBSCRIPTIONS = "deleteUserFromSubscriptions";
	public static final String DELETE_USER = "deleteUser";
	public static final String SAVE_USER = "saveUser";
	public static final String UPDATE_USER = "updateUser";
	public static final String ADD_CART_TO_SUBSCRIPTION_AND_DELETE = "addCartToSubscriptionAndDelete";
	public static final String GET_USER_WITH_PASSWORD_AND_NAME = "getUserWithPasswordAndName";
	public static final String GET_USER_WITH_NAME = "getUserWithName";
	public static final String CREATE_SUBSCRIPTION = "createSubscription";
	public static final String CLEAR_CART = "clearCart";
	public static final String GET_CART_PRICE = "getCartPrice";
	public static final String ADD_EDITION_TO_SUBSCRIPTION_FROM_CART = "addEditionsToSubscriptionFromCart";
	public static final String GET_LAST_SUBSCRIPTION_FROM_USER = "getLastSubscriptionFromUser";

	private static UserSQLManager editionSQLManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.userSQL";

	public static UserSQLManager getInstance() {
		if (editionSQLManager == null)
			editionSQLManager = new UserSQLManager(NAME);
		return editionSQLManager;
	}

	private UserSQLManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}
