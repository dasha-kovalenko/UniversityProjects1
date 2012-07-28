package levkat.chatfilews.core;

import java.util.ArrayList;
import levkat.chatfilews.iface.IChatService;

public class Chat implements IChatService
{
	public ChatPerson[] getAllUsers()
	{
		ArrayList<ChatPerson> users = DataAccessLayer.getInstance().getAllUsers();
		return users.toArray(new ChatPerson[users.size()]);
	}
	
	public ChatMessage[] getAllMessages()
	{
		ArrayList<ChatMessage> messages = DataAccessLayer.getInstance().getAllMessages();
		return messages.toArray(new ChatMessage[messages.size()]);
	}

	public void postMessage(String message, String nickname) 
	{
		 DataAccessLayer.getInstance().postMessage(message, nickname);
	}

	public ChatPerson[] getOnlineUsers() 
	{
		ArrayList<ChatPerson> onlineUsers = DataAccessLayer.getInstance().getOnlineUsers();
		return onlineUsers.toArray(new ChatPerson[onlineUsers.size()]);
	}

	public int login(String nickname, String password) 
	{
		int loginResult = DataAccessLayer.getInstance().loginUser(nickname, password);
		return loginResult;
	}

	public int registerNewUser(String nickname, String password)
	{
		int regResult = DataAccessLayer.getInstance().registerUser(nickname, password);
		return regResult;
	}

	public int logout(String nickname, String password)
	{
		int logoutResult = DataAccessLayer.getInstance().logoutUser(nickname, password);
		return logoutResult;
	}
}
