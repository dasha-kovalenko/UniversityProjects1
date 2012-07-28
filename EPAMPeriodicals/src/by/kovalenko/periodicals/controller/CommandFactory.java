package by.kovalenko.periodicals.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.command.*;
import by.kovalenko.periodicals.managers.CommandManager;

public class CommandFactory {

	private static CommandFactory commandFactory;

	private static final HashMap<String, ICommand> COMMAND_MAP;
	private static Logger log = Logger.getLogger(CommandFactory.class);

	static {
		COMMAND_MAP = new HashMap<String, ICommand>();
		// заполнение таблицы командами
		try {
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_CREATE),
					new EditionCreateCommand());

			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_EDIT),
					new EditionEditCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_DELETE),
					new EditionDeleteCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_LIST),
					new EditionListCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_SAVE),
					new EditionSaveCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_UPDATE),
					new EditionUpdateCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_ADD_TO_CART),
					new EditionAddToCartCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.EDITION_DELETE_FROM_CART),
					new EditionDeleteFromCartCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_ADD_CART_TO_SUBSCRIPTIONS),
					new UserAddCartToSubscriptionsCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_GET_CART),
					new UserGetCartCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_GET_SUBSCRUPTIONS),
					new UserGetSubscriptionsCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_LIST), new UserListCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_DELETE),
					new UserDeleteCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_SUBSCRIPTION_PAY),
					new UserSubscriptionPayCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_SUBSCRIPTION_SHOW),
					new UserSubscriptionShowCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_SUBSCRIPTION_DELETE),
					new UserSubscriptionDeleteCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_SUBSCRIPTION_CREATE),
					new UserSubscriptionCreateCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_UPDATE),
					new UserUpdateCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_EDIT), new UserEditCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_CREATE),
					new UserCreateCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_LOGIN), new UserLogInCommand());
			COMMAND_MAP.put(
					CommandManager.getInstance().getValue(
							CommandManager.USER_LOGOUT),
					new UserLogOutCommand());
			COMMAND_MAP
					.put(CommandManager.getInstance().getValue(
							CommandManager.ERROR), new ErrorCommand());
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}

	}

	/*
	 * создание единственного объекта по шаблону Singleton
	 */
	public static CommandFactory getInstance() {
		if (commandFactory == null)
			commandFactory = new CommandFactory();
		return commandFactory;
	}

	public ICommand getCommand(HttpServletRequest request) {
		String action = null;
		ICommand command = null;
		try {
			/*
			 * получение объекта, соответствующего команде
			 */
			action = request.getParameter(CommandManager.getInstance()
					.getValue(CommandManager.COMMAND));
			command = COMMAND_MAP.get(action);
			/*
			 * если команды не существует в текущем объекте, то показываем
			 * editionList
			 */
			if (command == null) {

				command = new EditionListCommand();
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}

		return command;
	}

}
