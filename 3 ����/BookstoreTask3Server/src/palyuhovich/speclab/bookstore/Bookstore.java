package palyuhovich.speclab.bookstore;

import java.rmi.*;
import java.util.*;

/**
 * Interface <code>Bookstore</code> extends <code>Remote</code>.
 * 
 * @author Yana Palyuhovich
 */
public interface Bookstore extends Remote {

	/**
	 * Adds the created book to the database.
	 * 
	 * @param book
	 * - the created book.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public void addBook(Book book) throws RemoteException;

	/**
	 * Edits the changed book to the database.
	 * 
	 * @param book
	 * - the changed book.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public void editBook(Book book) throws RemoteException;

	/**
	 * Removes the book from the database.
	 * 
	 * @param id
	 * - id of the book which is removed.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public void removeBook(int id) throws RemoteException;

	/**
	 * Adds the created topic to the database.
	 * 
	 * @param title
	 * - the title of the created topic.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public void addTopic(String title) throws RemoteException;

	/**
	 * Edits the changed topic to the database.
	 * 
	 * @param topic
	 * - the changed topic.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public void editTopic(Topic topic) throws RemoteException;

	/**
	 * Removes the topic from the database.
	 * 
	 * @param id
	 * - id of the topic which is removed.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public void removeTopic(int id) throws RemoteException;

	/**
	 * Returns the books which contain the selected topics from the database.
	 * 
	 * @param topics
	 * - List of the topics.
	 * @return List of books.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public List<Book> getBooks(List<Topic> topics) throws RemoteException;

	/**
	 * Returns all the topics from the database.
	 * 
	 * @return List of topic.
	 * @throws RemoteException
	 * if the connection to server lost.
	 */
	public List<Topic> getTopics() throws RemoteException;
}