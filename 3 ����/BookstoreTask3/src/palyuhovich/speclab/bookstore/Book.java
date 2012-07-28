package palyuhovich.speclab.bookstore;

import java.io.*;
import java.util.*;

/**
 * Class <code>Book</code>.
 * 
 * @author Yana Palyuhovich
 */
public class Book implements Serializable {

	private final int id;
	private final String title;
	private final String authors;
	private final List<Topic> topics = new ArrayList<Topic>();

	/**
	 * Constructs a book.
	 * 
	 * @param id
	 * - id of the book.
	 * @param title
	 * - title of the book.
	 * @param authors
	 * - authors of the book.
	 * @param topics
	 * - topics of the book.
	 */
	public Book(int id, String title, String authors, List<Topic> topics) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.topics.addAll(topics);
	}

	/**
	 * Returns id of the book.
	 * 
	 * @return id of the book.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns title of the book.
	 * 
	 * @return title of the book.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns authors of the book.
	 * 
	 * @return authors of the book.
	 */
	public String getAuthors() {
		return authors;
	}

	/**
	 * Returns topics of the book.
	 * 
	 * @return List of topics of the book.
	 */
	public List<Topic> getTopics() {
		return new ArrayList<Topic>(topics);
	}

	/**
	 * Sets topics of the book.
	 * 
	 * @param topics
	 * - List of topics which are set.
	 */
	public void setTopics(List<Topic> topics) {
		this.topics.clear();
		this.topics.addAll(topics);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (authors == null ? 0 : authors.hashCode());
		result = prime * result + id;
		result = prime * result + (title == null ? 0 : title.hashCode());
		result = prime * result + (topics == null ? 0 : topics.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Book other = (Book) obj;
		if (authors == null) {
			if (other.authors != null) {
				return false;
			}
		} else if (!authors.equals(other.authors)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (topics == null) {
			if (other.topics != null) {
				return false;
			}
		} else if (!topics.equals(other.topics)) {
			return false;
		}
		return true;
	}
}