package by.kovalenko.periodicals.managers;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public final class SubscriptionSQLManager {
	public static final String GET_SUBSCRIPTION = "getSubscription";
	public static final String LIST_SUBSCRIPTIONS = "listSubscriptions";
	public static final String SAVE_SUBSCRIPTION = "saveSubscription";
	public static final String UPDATE_SUBSCRIPTION = "updateSubscription";
	public static final String GET_EDITIONS_FROM_SUBSCRIPTION = "getEditionsFromSubscription";
	public static final String DELETE_EDITIONS_FROM_SUBSCRIPTION = "deleteEditionsFromSubscription";
	public static final String DELETE_SUBSCRIPTION = "deleteSubscription";

	private static SubscriptionSQLManager subscriptionSQLManager;
	private ResourceBundle resourceBundle;
	private static final String NAME = "by.kovalenko.periodicals.properties.subscriptionSQL";

	public static SubscriptionSQLManager getInstance() {
		if (subscriptionSQLManager == null)
			subscriptionSQLManager = new SubscriptionSQLManager(NAME);
		return subscriptionSQLManager;
	}

	private SubscriptionSQLManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}

	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}
