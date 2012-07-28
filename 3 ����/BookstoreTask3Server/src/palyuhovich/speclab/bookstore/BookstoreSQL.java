package palyuhovich.speclab.bookstore;

import java.sql.*;
import java.util.*;

/**
 * Class which works with MySQL.
 * 
 * @author Yana Palyuhovich
 */
public class BookstoreSQL {
	
	private final static String ADD_BOOK = "INSERT INTO `books` (`title`, `authors`) VALUES (?, ?);";
	private final static String EDIT_BOOK = "UPDATE `books` SET `title`=?, `authors`=? WHERE `id`=? LIMIT 1;";
	private final static String REMOVE_BOOK = "DELETE FROM `books` WHERE `id`=? LIMIT 1; DELETE FROM `books_topics` WHERE `book_id`=?;";
	
	private final static String ADD_TOPIC = "INSERT INTO `topics` (`topic`) VALUES (?);";
	private final static String EDIT_TOPIC = "UPDATE `topics` SET `topic`=? WHERE `id`=? LIMIT 1;";
	private final static String REMOVE_TOPIC = "DELETE FROM `topics` WHERE `id`=? LIMIT 1; DELETE FROM `books_topics` WHERE `topic_id`=?;";
	
	private final static String ADD_RELATIONS = "INSERT INTO `books_topics` (`book_id`, `topic_id`) VALUES";
	private final static String REMOVE_RELATIONS = "DELETE FROM `books_topics` WHERE `book_id`=?;";
	
	private final static String GET_BOOKS = "SELECT * FROM `books` ORDER BY `title`;";
	
	private final static String GET_BOOKS_PART_1 = "SELECT `id`, `title`, `authors` FROM `books` INNER JOIN (SELECT DISTINCT `book_id` FROM (SELECT `book_id`, count(`topic_id`) FROM  `books_topics` WHERE `topic_id` IN (";
	private final static String GET_BOOKS_PART_2 = ") GROUP BY `book_id` HAVING COUNT(topic_id) = ?) AS `needed_book_ids`) AS `book_ids` ON `book_ids`.`book_id` = `books`.`id` ORDER BY `title`;";
	
	private final static String GET_TOPICS = "SELECT * FROM `topics` ORDER BY `topic`;";
	private final static String GET_TOPICS_FOR_BOOK = "SELECT `id`, `topic` FROM `topics` INNER JOIN (SELECT DISTINCT `topic_id` FROM `books_topics` WHERE `book_id` = ?) AS `topic_ids` ON `topic_ids`.`topic_id` = `topics`.`id` ORDER BY `topic`;";
	
	private Connection connection;
	
	/**
	 * Connection to MySQL.
	 */
	public void connect() {
		try {
			connection = DriverManager
			        .getConnection("jdbc:mysql://localhost/bookstore?allowMultiQueries=true&user=root&password=root");
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the created book to the database.
	 * 
	 * @param book
	 *            - the created book.
	 */
	public void addBook(Book book) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(ADD_BOOK, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthors());
			ResultSet set = null;
			try {
				statement.executeUpdate();
				
				set = statement.getGeneratedKeys();
				set.next();
				int id = set.getInt(1);
				
				addRelations(id, getValidTopics(book.getTopics()));
			} finally {
				if (set != null) {
					set.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private List<Topic> getValidTopics(List<Topic> newTopics) {
		List<Topic> validTopics = new ArrayList<Topic>();
		List<Topic> allTopics = getTopics(-1);
		for (Topic newTopic : newTopics) {
			for (Topic oldTopic : allTopics) {
				if (newTopic.getId() == oldTopic.getId()) {
					validTopics.add(newTopic);
					break;
				}
			}
		}
		return validTopics;
	}
	
	private void addRelations(int book, List<Topic> topics) {
		if (topics.size() > 0) {
			StringBuffer query = new StringBuffer(ADD_RELATIONS);
			if (!topics.isEmpty()) {
				query.append(" (?, ?)");
			}
			for (int i = 1; i < topics.size(); i++) {
				query.append(", (?, ?)");
			}
			query.append(";");
			
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(query.toString());
				for (int i = 0; i < topics.size(); i++) {
					statement.setInt(i * 2 + 1, book);
					statement.setInt(i * 2 + 2, topics.get(i).getId());
				}
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void removeRelations(int id) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(REMOVE_RELATIONS);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Edits the changed book to the database.
	 * 
	 * @param book
	 *            - the changed book.
	 */
	public void editBook(Book book) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(EDIT_BOOK);
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthors());
			statement.setInt(3, book.getId());
			statement.executeUpdate();
			
			removeRelations(book.getId());
			addRelations(book.getId(), getValidTopics(book.getTopics()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Removes the book from the database.
	 * 
	 * @param id
	 *            - id of the book which is removed.
	 */
	public void removeBook(int id) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(REMOVE_BOOK);
			statement.setInt(1, id);
			statement.setInt(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Adds the created topic to the database.
	 * 
	 * @param title
	 *            - the title of the created topic.
	 */
	public void addTopic(String title) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(ADD_TOPIC);
			statement.setString(1, title);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Edits the changed topic to the database.
	 * 
	 * @param topic
	 *            - the changed topic.
	 */
	public void editTopic(Topic topic) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(EDIT_TOPIC);
			statement.setString(1, topic.getTitle());
			statement.setInt(2, topic.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Removes the topic from the database.
	 * 
	 * @param id
	 *            - id of the topic which is removed.
	 */
	public void removeTopic(int id) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(REMOVE_TOPIC);
			statement.setInt(1, id);
			statement.setInt(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Returns the books which contain the selected topics from the database.
	 * 
	 * @param topics
	 *            - List of the topics.
	 * @return List of books.
	 */
	public List<Book> getBooks(List<Topic> topics) {
		StringBuffer query = new StringBuffer();
		
		if (topics.isEmpty()) {
			query.append(GET_BOOKS);
		} else {
			query.append(GET_BOOKS_PART_1);
			if (topics.size() > 0) {
				query.append("?");
			}
			for (int i = 1; i < topics.size(); i++) {
				query.append(", ?");
			}
			
			query.append(GET_BOOKS_PART_2);
		}
		
		PreparedStatement statement = null;
		List<Book> books = new ArrayList<Book>();
		try {
			statement = connection.prepareStatement(query.toString());
			if (!topics.isEmpty()) {
				for (int i = 0; i < topics.size(); i++) {
					statement.setInt(i + 1, topics.get(i).getId());
				}
				statement.setInt(topics.size() + 1, topics.size());
			}
			ResultSet set = null;
			try {
				set = statement.executeQuery();
				while (set.next()) {
					books.add(new Book(set.getInt("id"), set.getString("title"), set.getString("authors"),
					        getTopics(set.getInt("id"))));
				}
			} finally {
				if (set != null) {
					set.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return books;
	}
	
	/**
	 * Returns the topics from the database.
	 * 
	 * @param book
	 *            - id of book or -1 if all the topics are returned.
	 * @return List of topic.
	 */
	public List<Topic> getTopics(int book) {
		PreparedStatement statement = null;
		List<Topic> topics = new ArrayList<Topic>();
		try {
			if (book == -1) {
				statement = connection.prepareStatement(GET_TOPICS);
			} else {
				statement = connection.prepareStatement(GET_TOPICS_FOR_BOOK);
				statement.setInt(1, book);
			}
			ResultSet set = null;
			try {
				set = statement.executeQuery();
				while (set.next()) {
					topics.add(new Topic(set.getInt("id"), set.getString("topic")));
				}
			} finally {
				if (set != null) {
					set.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return topics;
	}
	
	/**
	 * Closing connection to MySQL.
	 */
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
