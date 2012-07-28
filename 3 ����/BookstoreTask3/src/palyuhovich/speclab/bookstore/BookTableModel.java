package palyuhovich.speclab.bookstore;

import java.util.*;

import javax.swing.table.*;

/**
 * Class extends <code>AbstractTableModel</code> for <code>JTable</code>.
 * 
 * @author Yana Palyuhovich
 */
public class BookTableModel extends AbstractTableModel {
	
	private List<Book> books = new ArrayList<Book>();
	private String title = "";
	private String authors = "";
	private String topics = "";
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return title;
			case 1:
				return authors;
			case 2:
				return topics;
			default:
				return null;
		}
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public int getRowCount() {
		return books.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return books.get(rowIndex).getTitle();
		} else if (columnIndex == 1) {
			return books.get(rowIndex).getAuthors();
		} else if (columnIndex == 2) {
			return books.get(rowIndex).getTopics();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a book at the selected row.
	 * 
	 * @param rowIndex
	 *            - selected row.
	 * @return a book at the selected row.
	 */
	public Book getBookAt(int rowIndex) {
		return books.get(rowIndex);
	}
	
	/**
	 * Returns all books in the model.
	 * 
	 * @return List of books.
	 */
	public List<Book> getBooks() {
		return new ArrayList<Book>(books);
	}
	
	/**
	 * Adds new book at the end of the table.
	 * 
	 * @param book
	 *            - a book is added.
	 */
	public void addBook(Book book) {
		books.add(book);
		fireTableRowsInserted(books.size(), books.size());
	}
	
	/**
	 * Removes book from model.
	 * 
	 * @param id
	 *            - id of book which is removed.
	 * @return <ul> <li><code>true</code> if model contains the book.</li> <li><code>false</code> otherwise.</li> </ul>
	 */
	public boolean removeBook(int id) {
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId() == id) {
				books.remove(i);
				fireTableRowsDeleted(i, i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Clear all the model.
	 */
	public void clear() {
		books.clear();
		fireTableDataChanged();
	}
	
	/**
	 * Checks if model contains selected book.
	 * 
	 * @param b
	 *            - a book which is checked.
	 * @return <ul> <li><code>true</code> if model contains the book.</li> <li><code>false</code> otherwise.</li> </ul>
	 */
	public boolean contains(Book b) {
		return books.contains(b);
	}
	
	/**
	 * Returns index of selected book.
	 * 
	 * @param b
	 *            - selected book.
	 * @return index of the book.
	 */
	public int indexOf(Book b) {
		return books.indexOf(b);
	}
	
	/**
	 * Returns index of book by id.
	 * 
	 * @param id
	 *            - id of book.
	 * @return <ul> <li>index of book if model contains the book.</li> <li>-1 otherwise.</li> </ul>
	 */
	public int getIndexById(int id) {
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Sets a book at selected index.
	 * 
	 * @param index
	 *            - selected index.
	 * @param b
	 *            - a book.
	 */
	public void setBookAt(int index, Book b) {
		books.set(index, b);
		fireTableRowsUpdated(index, index);
	}
	
	public void setTitles(ResourceBundle bundle) {
		title = bundle.getString("model_title");
		authors = bundle.getString("model_authors");
		topics = bundle.getString("model_topics");
	}
}