package by.kovalenko.football.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import by.kovalenko.football.command.MatchCreateCommand;
import by.kovalenko.football.command.MatchDeleteCommand;
import by.kovalenko.football.command.MatchEditCommand;
import by.kovalenko.football.command.MatchListCommand;
import by.kovalenko.football.command.MatchSaveCommand;
import by.kovalenko.football.command.ICommand;
import by.kovalenko.football.command.MatchUpdateCommand;


public class RequestHelper {

	private static RequestHelper requestHelper;

	private static final HashMap<String, ICommand> COMMAND_MAP;

	static {
		COMMAND_MAP = new HashMap<String, ICommand>();
		COMMAND_MAP.put("matchCreate", new MatchCreateCommand());
		COMMAND_MAP.put("matchEdit", new MatchEditCommand());
		COMMAND_MAP.put("matchDelete", new MatchDeleteCommand());
		COMMAND_MAP.put("matchList", new MatchListCommand());
		COMMAND_MAP.put("matchSave", new MatchSaveCommand());
		COMMAND_MAP.put("matchUpdate", new MatchUpdateCommand());
	}

	public static RequestHelper getInstance() {
		if (requestHelper == null)
			requestHelper = new RequestHelper();
		return requestHelper;
	}

	public ICommand getCommand(HttpServletRequest request) {
		String action = request.getParameter("command");
		ICommand command = COMMAND_MAP.get(action);
		if (command == null) {
			command = new MatchListCommand();
		}
		return command;
	}

}
