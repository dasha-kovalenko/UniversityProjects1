//package by.aig.serverrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

//import by.aig.common.IMeetingDAO;

public class ServerRunner {

	public static void main(String[] args) throws RemoteException,
			IllegalArgumentException, NotBoundException, MalformedURLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		LocateRegistry.createRegistry(1099);
		IMeetingDAO meetingDAO = new MeetingDAO();
		Naming.rebind("server.meetingDAO", meetingDAO);
		System.out.println("server.RMI Server is ready.");
	}

}