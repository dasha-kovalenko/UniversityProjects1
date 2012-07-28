package levkat.chatfilews.core;

public class ChatPerson 
{
	public String nickname;
	
	/**
	 * unique id of the message;
	 */
	public long id;	
	
	/**
	 * PAssword of the user
	 */
	public String password;
	
	public Boolean state;
	
	/**
	 * Default constructor 
	 */
	public ChatPerson() 
	{
		super();
	}
	
	/**
	 * Constructor 
	 * @param nickname
	 * @param passwd
	 * @param state
	 * @param id
	 */
	public ChatPerson(String nickname, String passwd, Boolean state, long id) 
	{
		this.nickname = nickname;
		this.password = passwd;
		this.state = state;
		this.id = id;
	}
}
