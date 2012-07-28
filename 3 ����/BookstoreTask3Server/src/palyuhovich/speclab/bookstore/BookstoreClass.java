package palyuhovich.speclab.bookstore;

import java.util.*;

/**
 * Class which implements {@link Bookstore}.
 * 
 * @author Yana Palyuhovich
 */
public class BookstoreClass implements Bookstore {
	
	private BookstoreXML bookstoreXML;
	
	/**
	 * Returns BookstoreSQL which executes SQL queries.
	 * 
	 * @return BookstoreSQL.
	 */
	public BookstoreXML getBookstoreXML() {
		return bookstoreXML;
	}
	
	/**
	 * Set BookstoreSQL which executes SQL queries.
	 * 
	 * @param b
	 *            - BookstoreSQL.
	 */
	public void setBookstoreXML(BookstoreXML b) {
		this.bookstoreXML = b;
	}
	
	@Override
	public synchronized void addBook(Book book) {
		bookstoreXML.addBook(book);
	}
	
	@Override
	public synchronized void editBook(Book book) {
		bookstoreXML.editBook(book);
	}
	
	@Override
	public synchronized void removeBook(int id) {
		bookstoreXML.removeBook(id);
	}
	
	@Override
	public synchronized void addTopic(String title) {
		bookstoreXML.addTopic(title);
	}
	
	@Override
	public synchronized void editTopic(Topic topic) {
		bookstoreXML.editTopic(topic);
	}
	
	@Override
	public synchronized void removeTopic(int id) {
		bookstoreXML.removeTopic(id);
	}
	
	@Override
	public synchronized List<Book> getBooks(List<Topic> topics) {
		return bookstoreXML.getBooks(topics);
	}
	
	@Override
	public synchronized List<Topic> getTopics() {
		return bookstoreXML.getTopics();
	}
}
