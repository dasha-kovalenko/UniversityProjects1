import java.rmi.Naming;
import java.rmi.RemoteException;


public class FootballLookupServer {

	/**
	 * @param args
	 */
	public FootballLookupServer(String filename) throws RemoteException{
		try {
			FootballLookup l = new FootballLookupImpl(filename); 
			Naming.rebind("//microsof-eb58cc/LookupService", l);
		} catch (Exception e){
			System.out.println("Trouble:" + e);
		}
	}
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		new FootballLookupServer("Teams.txt");

	}

}
