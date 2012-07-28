import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.StringTokenizer;
import java.util.Vector;


public class FootballLookupImpl extends UnicastRemoteObject implements FootballLookup{
	private Vector<String> teams = new Vector<String>();

	public FootballLookupImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public FootballLookupImpl(String filename) throws RemoteException{
		try {
			FileReader r = new FileReader(filename);
			BufferedReader reader = new BufferedReader(r);
			 String s = null;
		 	 while ((s = reader.readLine()) != null)
			 	 teams.add(s);
		 	 reader.close();
		 	 r.close();

		} catch (FileNotFoundException fnfe) {
			 System.err.println("Exception" + fnfe);
			 System.exit(1);
		} catch (IOException ioe){
			 System.err.println("Exception" + ioe);
			 System.exit(1);
		}

	}
	@Override
	public String findInfo(String info) throws RemoteException {
		// TODO Auto-generated method stub
		 String returnString = null;
		 if (info == null) return null;
		 else{
			 info = info.toLowerCase();
			 int n = teams.size();
			 for (int i = 0; i < n; i++){
				String tmp = (String)teams.get(i);
				StringTokenizer st = new StringTokenizer(tmp,",");
				if (info.equals(st.nextToken())){
					returnString = tmp;
				}
			 }
		 }
		 return returnString;
	}

}
