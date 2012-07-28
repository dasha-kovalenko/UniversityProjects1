package by.kovalenko.periodicals.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import by.kovalenko.periodicals.command.*;

public class CommandFactory {

	private static CommandFactory commandFactory;

	private static final HashMap<String, ICommand> COMMAND_MAP;

	static {
		COMMAND_MAP = new HashMap<String, ICommand>();
		COMMAND_MAP.put("editionCreate", new EditionCreateCommand());
		COMMAND_MAP.put("editionEdit", new EditionEditCommand());
		COMMAND_MAP.put("editionDelete", new EditionDeleteCommand());
		COMMAND_MAP.put("editionList", new EditionListCommand());
		COMMAND_MAP.put("editionSave", new EditionSaveCommand());
		COMMAND_MAP.put("editionUpdate", new EditionUpdateCommand());
		COMMAND_MAP.put("editionAddToCart", new EditionAddToCartCommand());
		COMMAND_MAP.put("editionDeleteFromCart",
				new EditionDeleteFromCartCommand());
		COMMAND_MAP.put("userAddCartToSubscriptions",
				new UserAddCartToSubscriptionsCommand());
		COMMAND_MAP.put("userGetCart", new UserGetCartCommand());
		COMMAND_MAP.put("userGetSubscriptions",
				new UserGetSubscriptionsCommand());
		COMMAND_MAP.put("userList", new UserListCommand());
		COMMAND_MAP.put("userDelete", new UserDeleteCommand());
		COMMAND_MAP
				.put("userSubscriptionPay", new UserSubscriptionPayCommand());
		COMMAND_MAP.put("userSubscriptionShow",
				new UserSubscriptionShowCommand());
		COMMAND_MAP.put("userSubscriptionDelete",
				new UserSubscriptionDeleteCommand());
		COMMAND_MAP.put("userSubscriptionCreate",
				new UserSubscriptionCreateCommand());
		COMMAND_MAP.put("userUpdate", new UserUpdateCommand());
		COMMAND_MAP.put("userEdit", new UserEditCommand());
		COMMAND_MAP.put("userCreate", new UserCreateCommand());
		COMMAND_MAP.put("userLogIn", new UserLogInCommand());
		COMMAND_MAP.put("userLogOut", new UserLogOutCommand());
		COMMAND_MAP.put("error", new ErrorCommand());

	}

	public static CommandFactory getInstance() {
		if (commandFactory == null)
			commandFactory = new CommandFactory();
		return commandFactory;
	}

	public ICommand getCommand(HttpServletRequest request) {
		String action = request.getParameter("command");
		ICommand command = COMMAND_MAP.get(action);
		if (command == null) {
			command = new EditionListCommand();
		}
		return command;
	}

}
