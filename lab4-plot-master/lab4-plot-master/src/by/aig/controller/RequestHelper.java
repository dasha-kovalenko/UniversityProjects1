package by.aig.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import by.aig.command.ICommand;
import by.aig.command.PlotMasterErrorCommand;
import by.aig.command.PlotMasterStep1Command;
import by.aig.command.PlotMasterStep2Command;
import by.aig.command.PlotMasterStep3Command;

public class RequestHelper {

	private static RequestHelper requestHelper;

	private static final HashMap<String, ICommand> COMMAND_MAP;

	static {
		COMMAND_MAP = new HashMap<String, ICommand>();
		COMMAND_MAP.put("step1", new PlotMasterStep1Command());
		COMMAND_MAP.put("step2", new PlotMasterStep2Command());
		COMMAND_MAP.put("step3", new PlotMasterStep3Command());
		COMMAND_MAP.put("error", new PlotMasterErrorCommand());
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
			command = new PlotMasterStep1Command();
		}
		return command;
	}

}
