package levkat.chatfilews.core;

import java.util.Date;

public class ChatMessage 
{
	/**
	 * Date/time of the arrival of the message to the server.
	 */
	public Date timestamp;

	/**
	 * unique id of the message;
	 */
	public long id;	
	
	/**
	 * Author of this message.
	 */
	public ChatPerson author;
	
	/**
	 * Text of this message
	 */
	public String text;


	/**
	 * Default constructor
	 */
	public ChatMessage() 
	{
		super();
	}

	public ChatMessage(String text, ChatPerson author, long id, Date timestamp) 
	{
	    this.text = text;
	    this.author = author;
	    this.timestamp = timestamp;
	    this.id = id;
	}
}
