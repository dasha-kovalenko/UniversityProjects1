import java.rmi.Remote;
import java.rmi.RemoteException;


public interface FootballLookup extends Remote {
	public String findInfo(String info) throws RemoteException;

}
