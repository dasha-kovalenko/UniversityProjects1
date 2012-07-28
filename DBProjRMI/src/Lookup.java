import java.rmi.*;

public interface Lookup extends Remote{
	public String findInfo(String info) throws RemoteException;
}
