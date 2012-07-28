package by.kdv.serverrmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ServerRunner {

	private static ServerFrame serverFrame;

	public static void main(String[] args) throws RemoteException,
			IllegalArgumentException, NotBoundException, MalformedURLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		setServerFrame(new ServerFrame(1099, "Match.xml", "Match.xsd"));
		
	}

	public static ServerFrame getServerFrame() {
		return serverFrame;
	}

	public static void setServerFrame(ServerFrame serverFrame) {
		ServerRunner.serverFrame = serverFrame;
	}

}