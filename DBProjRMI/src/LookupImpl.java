import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.StringTokenizer;
import java.util.Vector;


public class LookupImpl extends java.rmi.server.UnicastRemoteObject implements Lookup {

	private Vector<String> dataBase = new Vector<String>();
	
	public LookupImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public LookupImpl(String filename) throws RemoteException{
		//super();
		try {
			FileReader r = new FileReader(filename);
			BufferedReader reader = new BufferedReader(r);
			String s = null;
		 	while ((s = reader.readLine()) != null)
			 	dataBase.add(s);
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
		 String returnString = null;
		 if (info == null) return null;
		 else{
			 info = info.toLowerCase();
			 int n = dataBase.size();
			 for (int i = 0; i < n; i++){
				String tmp = (String)dataBase.get(i);
				StringTokenizer st = new StringTokenizer(tmp,",");
				if (info.equals(st.nextToken())){
					returnString = info;
					//System.out.println(returnString);
				}
			 }
		 }
		 return returnString;
	}

}
