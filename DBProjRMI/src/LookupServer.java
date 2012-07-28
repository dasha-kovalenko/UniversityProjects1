import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class LookupServer  {
//	 private Vector save = new Vector();

	 public LookupServer(String filename){ 
		try {
			Lookup l = new LookupImpl(filename); 
			Naming.rebind("//microsof-eb58cc/LookupService", l);
		} catch (Exception e){
			System.out.println("Trouble:" + e);
		}
}

	/* public String findInfo(String info){
		 String returnString = null;
		 if (info == null) return null;
		 else{
			 info = info.toLowerCase();
			 int n = save.size();
			 for (int i = 0; i < n; i++){
				String tmp = (String)save.get(i); 
				if (info.equals(tmp))
					returnString = info;
			 }
		 }
		 return returnString;
	 }*/
		public static void main(String[] args) throws RemoteException {
			// TODO Auto-generated method stub
			new LookupServer("PHONE.txt");
		}
}