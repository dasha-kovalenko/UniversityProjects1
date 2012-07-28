package by.aig.serverrmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ServerRunner {

	public static void main(String[] args) throws RemoteException,
			IllegalArgumentException, NotBoundException, MalformedURLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		ServerFrame serverFrame = new ServerFrame(1099);
	}

}