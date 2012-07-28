package levkat.chatfilews.iface;

import levkat.chatfilews.core.ChatMessage;
import levkat.chatfilews.core.ChatPerson;

public interface IChatService 
{
	/**
	 * Gets all registered users.
	 * @return array of all users. When there are no users, an empty array is
	 * returned. The null value is never returned.
	 */
	public ChatPerson[] getAllUsers();
	
	/**
	 * Gets all on-line users.
	 * @return array of on-line users. When there are no users, an empty array is
	 * returned. The null value is never returned.
	 */
	public ChatPerson[] getOnlineUsers();
	
	/**
	 * Gets all messages sent to the server.
	 * @return array of all messages. When there are no messages, an empty array is
	 * returned. The null value is never returned.
	 */
	public ChatMessage[] getAllMessages();
	
	/**
	 * Posts new message to the chat server
	 * @param message - the content of the message
	 * @param nickname - the nick name of the posting person
	 */
	public void postMessage(String message, String nickname);
	
	/**
	 * Login user
	 * @param nickname
	 * @param password
	 * @return 0 - if login is successful, -1 - if login is not successful (wrong name or password, 
	 * user is already logged in)
	 */
	public int login(String nickname, String password);
	
	/**
	 * Registers new user
	 * @param nickname
	 * @param password
	 * @return 0 - if registration is successful, -1 - if such user is already existed
	 */
	public int registerNewUser(String nickname, String password);
	
	/**
	 * Logout
	 * @param nickname
	 * @param password
	 * @return 0 - if logout is successful
	 */
	public int logout(String nickname, String password);
}
