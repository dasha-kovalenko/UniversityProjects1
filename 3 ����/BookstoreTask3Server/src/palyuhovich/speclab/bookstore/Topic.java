package palyuhovich.speclab.bookstore;

import java.io.*;

/**
 * Class <code>Topic</code>.
 * 
 * @author Yana Palyuhovich
 */
public class Topic implements Serializable {

	private final int id;
	private final String title;

	/**
	 * Constructs a topic.
	 * 
	 * @param id
	 * - id of the topic.
	 * @param title
	 * - the title of the topic.
	 */
	public Topic(int id, String title) {
		this.id = id;
		this.title = title;
	}

	/**
	 * Returns id of topic.
	 * 
	 * @return id of topic.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns title of topic.
	 * 
	 * @return title of topic.
	 */
	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return getTitle();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (title == null ? 0 : title.hashCode());
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
		Topic other = (Topic) obj;
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
		return true;
	}
}