package by.aig.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import by.aig.command.CandidateCreateCommand;
import by.aig.command.CandidateDeleteCommand;
import by.aig.command.CandidateEditCommand;
import by.aig.command.CandidateGetCommand;
import by.aig.command.CandidateListCommand;
import by.aig.command.CandidateSaveCommand;
import by.aig.command.ICommand;

public class RequestHelper {

	private static RequestHelper requestHelper;

	private static final HashMap<String, ICommand> COMMAND_MAP;

	static {
		COMMAND_MAP = new HashMap<String, ICommand>();
		COMMAND_MAP.put("candidateCreate", new CandidateCreateCommand());
		COMMAND_MAP.put("candidateEdit", new CandidateEditCommand());
		COMMAND_MAP.put("candidateDelete", new CandidateDeleteCommand());
		COMMAND_MAP.put("candidateList", new CandidateListCommand());
		COMMAND_MAP.put("candidateSave", new CandidateSaveCommand());
		COMMAND_MAP.put("candidateGet", new CandidateGetCommand());
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
			command = new CandidateListCommand();
		}
		return command;
	}

}
