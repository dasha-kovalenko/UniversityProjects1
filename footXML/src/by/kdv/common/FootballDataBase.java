package by.kdv.common;
import java.net.SocketException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;



public interface FootballDataBase extends Remote {
	
	List<Match> selectMatches(/*JFrame f, */String team, Date startDate, Date endDate) throws RemoteException;
		
	void insertMatch(String team1, String team2, String count, Date date) throws RemoteException;
		
	void deleteMatch(int id) throws RemoteException;
	
	List<Match> showDataBase(/*JFrame frame*/) throws RemoteException,SocketException;

}
