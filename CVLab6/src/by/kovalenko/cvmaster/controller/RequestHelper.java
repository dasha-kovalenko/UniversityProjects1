package by.kovalenko.cvmaster.controller;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import by.kovalenko.cvmaster.command.*;

public class RequestHelper {

	private static RequestHelper requestHelper;

	private static final HashMap<String, ICommand> COMMAND_MAP;

	static {
		COMMAND_MAP = new HashMap<String, ICommand>();
		COMMAND_MAP.put("step1", new CVMasterStep1Command());
		COMMAND_MAP.put("step2", new CVMasterStep2Command());
		COMMAND_MAP.put("step3", new CVMasterStep3Command());
		COMMAND_MAP.put("step4", new CVMasterStep4Command());
		COMMAND_MAP.put("step5", new CVMasterStep5Command());
		
		COMMAND_MAP.put("error", new CVMasterErrorCommand());
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
			command = new CVMasterStep1Command();
		}
		return command;
	}

}

