import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

public class ServerRunner {

	public static void main(String[] args) throws RemoteException,
											IllegalArgumentException, NotBoundException, MalformedURLException,
											InstantiationException, IllegalAccessException, ClassNotFoundException, 
											SQLException {
		LocateRegistry.createRegistry(1099);
		FootballDataBase footballDB = new FootballServer();
		Naming.rebind("server.footballdb", footballDB);
		System.out.println("server.RMI Server is ready.");

	}

}





// TODO Auto-generated method stub
/*String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
try {
	Class.forName(driverName);
} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

try {
	
	Connection cn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/footballdb", "player", "");
	
	Statement st = cn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM matches");
	while (rs.next())
	{
		System.out.println("Team1 = " +  rs.getString("team_1")+" team2 = " +  rs.getString("team_2")+" Count = " + rs.getString("count")+" Date = " + rs.getString("matchdate"));
	}
	rs.close();		
	st.close();
	cn.close();

	cn.close();

} catch (SQLException e) {
	e.printStackTrace();
}
*/