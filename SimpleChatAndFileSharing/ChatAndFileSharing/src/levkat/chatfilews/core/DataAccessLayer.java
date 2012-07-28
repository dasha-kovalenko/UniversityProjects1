package levkat.chatfilews.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataAccessLayer 
{
	/** Singleton instance */
	private static final DataAccessLayer instance = new DataAccessLayer();
	private static final boolean NO = false;
	private static final int SUCCESS = 0;
	private static final int FAILING = -1;
	
	private String JDBC_DRIVER_CLASS = "org.sqlite.JDBC";
	private String JDBC_URL = "jdbc:sqlite:D:\\Projects\\Java\\SimpleChatAndFileSharing\\ChatAndFileSharing\\database\\Chatdb.sqlite";
	
	public String sDriver = "";
	public String sUrl = null;
	public int iTimeout = 30;
	public Connection conn = null;
	public Statement statement = null;
	
	
	public DataAccessLayer()
	{}
	
	/** Singleton instance getter 
	 * This method returns the unique instance
	 * @return the unique instance
	 */
	public static final DataAccessLayer getInstance() 
	{
		return instance;
	}	
	
	private void init(String sDriverVar, String sUrlVar)
	{
		this.setDriver(sDriverVar);
		this.setUrl(sUrlVar);
		this.setConnection();
		this.setStatement(); 
	}
	
	private void setDriver(String sDriverVar)
	{
		this.sDriver = sDriverVar;
	}
	
	private void setUrl(String sUrlVar)
	{
		this.sUrl = sUrlVar;
	}
	
	private void setConnection() 
	{
		try 
		{
			Class.forName(this.sDriver);
			this.conn = DriverManager.getConnection(this.sUrl);
		} 
		catch (ClassNotFoundException e) 
		{
			this.conn = null;
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			this.conn = null;
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() 
	{
		return this.conn;
	}
	
	private void setStatement() 
	{
		if (this.conn == null) 
		{
			this.setConnection();
		}
		try 
		{
			this.statement = this.conn.createStatement(); 
			this.statement.setQueryTimeout(this.iTimeout); // set timeout to 30 sec.
		} 
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
	
	public Statement getStatement()
	{
		return this.statement;
	}
	
	private void executeStatement(String instruction) throws SQLException 
	{
		try 
		{
			this.statement.executeUpdate(instruction);
		} 
		catch (SQLException e) 
		{
			System.out.println(instruction);
			System.err.println(e);
		}
	}
	
	// processes an array of instructions e.g. a set of SQL command strings passed from a file
	//NB you should ensure you either handle empty lines in files by either removing them or parsing them out
	// since they will generate spurious SQLExceptions when they are encountered during the iteration....
	public void executeStatement(String[] instructionSet) throws SQLException 
	{
		try 
		{
			for (int i = 0; i < instructionSet.length; i++) 
			{
				this.executeStatement(instructionSet[i]);
			}
		}
		catch (SQLException e) 
		{
			System.err.println(e);
		}
	}
	
	private ResultSet executeQuery(String instruction) throws SQLException 
	{
		ResultSet rs = null;
		try 
		{
			rs = this.statement.executeQuery(instruction);
			return rs;
		} 
		catch (SQLException e) 
		{
			System.err.println(e);
			return rs; // will be null so you can handle it in the calling statement as a null
		}
	}
	
	private void closeConnection() 
	{
		try 
		{
			this.conn.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public ArrayList<ChatPerson> getAllUsers()
	{
		ArrayList<ChatPerson> users = new ArrayList<ChatPerson>();
		try
		{
			this.init(JDBC_DRIVER_CLASS, JDBC_URL);
			
			ResultSet result = this.executeQuery("SELECT Id, Name, Password, State FROM User");
			
			while (result.next()) 
			{
				String name = result.getString("Name");
				String pswd = result.getString("Password");
				boolean state = result.getBoolean("State");
				long id = result.getLong("Id");
				
				ChatPerson user = new ChatPerson(name, pswd, state, id);
				
				users.add(user);
			}
           
			this.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return users;
	}
	
	public ArrayList<ChatMessage> getAllMessages()
	{
		ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
		try
		{
			this.init(JDBC_DRIVER_CLASS, JDBC_URL);
			
			
			ResultSet result = this.executeQuery("SELECT Id, UserId, Text, Time FROM Message");
			
			while (result.next()) 
			{
				String text = result.getString("Text");
				
				String dateString = result.getString("Time");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				java.util.Date date_util = new Date();
				try {
					date_util = dateFormat.parse(dateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				long id = result.getLong("Id");
				long userId = result.getLong("UserId");
				
				ChatPerson author = new ChatPerson(null, null, null, userId);
				ChatMessage msg = new ChatMessage(text, author, id, date_util /*date_sql.toString()*/);
				messages.add(msg);
			}
			
			for (int i = 0; i< messages.size(); i++)
			{
				try
				{
					ChatMessage msg = messages.get(i);
					ChatPerson author = msg.author;
					ResultSet uRes = this.executeQuery("SELECT Name, Password, State FROM User WHERE Id == '" + author.id + "'");
					
					msg.author.password = uRes.getString("Password");
					msg.author.state = uRes.getBoolean("State");
					msg.author.nickname = uRes.getString("Name");
				}
				catch (SQLException e)
				{
					System.out.println(e);
				}
			}
           
			this.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return messages;
		
	}
	
	/**
	* Get at the moment within ISO8601 format.
	* @return
	* Date and time in ISO8601 format.
	*/
	private String getSqliteDateString(Date date)
	{
		String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ISO8601);
		return sdf.format(date); 
	} 
	
	synchronized public void postMessage(String message, String nickname) 
	{
		if(message == null) throw 
			new IllegalArgumentException("The message argument to the postMessage method may not be null.");
		if(nickname == null) throw 
			new IllegalArgumentException("The nickname argument to the postMessage method may not be null.");
		
		try
		{
			this.init(JDBC_DRIVER_CLASS, JDBC_URL);
			
			ResultSet result = this.executeQuery("SELECT Id, Password, State FROM User WHERE Name == '" + nickname + "'");
			
			String pswd = result.getString("Password");
			boolean state = result.getBoolean("State");
			long id = result.getLong("Id");
			
			ChatPerson author = new ChatPerson(nickname, pswd, state, id);
			
			ChatMessage msg = new ChatMessage(message, author, 0, new Date());
			
			this.addMessage(msg);
			this.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	} 
	
	private void addMessage(ChatMessage msg)
	{
		 try 
		 { 
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // set the format to sql date time
			 Date date = new Date();

			this.executeStatement("INSERT INTO Message (UserId, Text, Time) VALUES " + 
							"(" + "'" + msg.author.id + "', " +
								  "'" + msg.text 	  + "', " +  
								  "'" + dateFormat.format(date) /*getSqliteDateString(msg.timestamp)*/ + "'" +
							")" );
			
        } 
		catch (Exception e) 
		{ 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        } 

	}
	
	synchronized public int loginUser(String nickname, String passwd) 
	{
		return this.updateUserStatus(nickname, passwd, true);
	} 
	
	synchronized public int logoutUser(String nickname, String passwd) 
	{
		return this.updateUserStatus(nickname, passwd, false);
	}
	
	private int updateUserStatus(String nickname, String passwd, boolean newState)
	{
		if(nickname == null) throw 
			new IllegalArgumentException("The nickname argument to the updateUserStatus method may not be null.");
		if(passwd == null) throw 
			new IllegalArgumentException("The passwd argument to the updateUserStatus method may not be null.");
		
		int loginRes = FAILING;
		
		try
		{
			this.init(JDBC_DRIVER_CLASS, JDBC_URL);
			
			ResultSet result = this.executeQuery("SELECT Id, State FROM User WHERE Name = '" + nickname + "' AND Password = '" + passwd + "'");
			
			boolean isUserFound = result.next();
			
			if(isUserFound == NO)
			{
				loginRes = FAILING;
			}
			else
			{
				long id = result.getLong("Id");
				
				ChatPerson author = new ChatPerson(nickname, passwd, newState, id);
				this.executeUpdateUserStatus(author);
				
				loginRes = SUCCESS;
			}
			
			this.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return loginRes;
	}
	
	synchronized public int registerUser(String nickname, String passwd) 
	{
		if(nickname == null) throw 
			new IllegalArgumentException("The nickname argument to the registerUser method may not be null.");
		if(passwd == null) throw 
			new IllegalArgumentException("The passwd argument to the registerUser method may not be null.");
		
		int isRegistered = SUCCESS;
		
		try
		{
			this.init(JDBC_DRIVER_CLASS, JDBC_URL);
			
			ResultSet result = this.executeQuery("SELECT Id, Password, State FROM User WHERE Name == '" + nickname + "'");
			
			boolean isUserFound = result.next();
			
			if(isUserFound == NO)
			{
				ChatPerson author = new ChatPerson(nickname, passwd, true, 0);
				
				this.addUser(author);
				
				isRegistered = SUCCESS;
			}
			else
			{
				isRegistered = FAILING;
			}
			
			this.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return isRegistered;
	} 
	
	private void addUser(ChatPerson user)
	{
		 try 
		 { 
			// user.state?1:0 - Convert boolean to int type
			this.executeStatement("INSERT INTO User (Name, Password, State) VALUES " + 
							"(" + "'" + user.nickname + "', " +
								  "'" + user.password + "', " +  
								  "'" + (user.state?1:0)    + "'" +
							")" );
			
        } 
		catch (Exception e) 
		{ 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        } 

	}
	
	private void executeUpdateUserStatus(ChatPerson user)
	{
		try 
		{ 
			// user.state?1:0 - Convert boolean to int type
			this.executeStatement("UPDATE User SET State='" + (user.state?1:0) + "' WHERE Id='" + user.id + "'");
		} 
		catch (Exception e) 
		{ 
           System.err.println("Got an exception! "); 
           System.err.println(e.getMessage()); 
       } 
	}
	
	public ArrayList<ChatPerson> getOnlineUsers()
	{
		ArrayList<ChatPerson> users = new ArrayList<ChatPerson>();
		try
		{
			this.init(JDBC_DRIVER_CLASS, JDBC_URL);
			
			ResultSet result = this.executeQuery("SELECT Id, Name, Password, State FROM User WHERE State == '" + 1 + "'");
			
			while (result.next()) 
			{
				String name = result.getString("Name");
				String pswd = result.getString("Password");
				boolean state = result.getBoolean("State");
				long id = result.getLong("Id");
				
				ChatPerson user = new ChatPerson(name, pswd, state, id);
				
				users.add(user);
			}
           
			this.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return users;
	}
}