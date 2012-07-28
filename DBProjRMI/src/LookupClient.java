import java.rmi.Naming;
//import java.rmi.RMISecurityManager;


public class LookupClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//RMISecurityManager security = new RMISecurityManager();
			//System.setSecurityManager(security);
/*			String host = "localhost";
			String server = "LookupServer";
			String name = "rmi://"+ host + "/" + server;
			Lookup look_obj = (Lookup)Naming.lookup(name);
*/	//		String results = look_obj.findInfo(args[0]);
			Lookup l = (Lookup) Naming.lookup ("rmi://localhost/LookupService"); 
			String results = l.findInfo(args[0]);
			if (results == null)
				System.err.println("** not found **");
			else
				System.out.println(results);
		} catch (Throwable e) {
			System.err.println("Exception: " + e);
			System.exit(1);
		}
	}

}
