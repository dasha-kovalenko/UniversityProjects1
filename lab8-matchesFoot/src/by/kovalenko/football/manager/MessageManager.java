package by.kovalenko.football.manager;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class MessageManager {
	
	public static final String INCORRECT_DATA = "incorrectData";
	public static final String INCORRECT_ID_ERROR_MESSAGE = "incorrectIdErrorMessage";
	public static final String STANDARD_ERROR_MESSAGE = "standardErrorMessage";

	private static final String NAME = "by.kovalenko.football.properties.message";
	private static MessageManager messageManager;
	private ResourceBundle resourceBundle;

	public static MessageManager getInstance() {
		if (messageManager == null)
			messageManager = new MessageManager(NAME);
		return messageManager;
	}

	private MessageManager(String name) {
		resourceBundle = ResourceBundle.getBundle(name);
	}
	
	public String getValue(String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(), "utf-8");
	}

}
