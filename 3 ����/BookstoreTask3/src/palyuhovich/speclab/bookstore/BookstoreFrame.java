package palyuhovich.speclab.bookstore;

import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

/**
 * The main class of program extends <code>JFrame</code>.
 * 
 * @author Yana Palyuhovich
 */
public class BookstoreFrame extends JFrame {
	
	private ConnectDialog connectDialog;
	private BookDialog bookDialog;
	private TopicDialog topicDialog;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu bookMenu;
	private JMenu topicMenu;
	private JMenu languageMenu;
	
	private JPanel booksPanel;
	private JPanel topicsPanel;
	private JSplitPane splitPane;
	
	private JTable bookTable;
	private BookTableModel bookModel;
	private JList topicList;
	private DefaultListModel topicModel;
	
	private Bookstore bookstore;
	
	private boolean refresing = false;
	private String errorMessage;
	private String error;
	private RefreshAction refreshAction;
	private ExitAction exitAction;
	private AddBookAction addBookAction;
	private EditBookAction editBookAction;
	private RemoveBookAction removeBookAction;
	private AddTopicAction addTopicAction;
	private EditTopicAction editTopicAction;
	private RemoveTopicAction removeTopicAction;
	private EnAction enAction;
	private RuAction ruAction;
	
	/**
	 * Constructs a frame.
	 */
	public BookstoreFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// Can't change look-n-feel - do nothing anyway
		}
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		refreshAction = new RefreshAction();
		exitAction = new ExitAction();
		addBookAction = new AddBookAction();
		editBookAction = new EditBookAction();
		removeBookAction = new RemoveBookAction();
		addTopicAction = new AddTopicAction();
		editTopicAction = new EditTopicAction();
		removeTopicAction = new RemoveTopicAction();
		enAction = new EnAction();
		ruAction = new RuAction();
		
		connectDialog = new ConnectDialog();
		bookDialog = new BookDialog();
		topicDialog = new TopicDialog();
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		fileMenu.add(refreshAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		menuBar.add(fileMenu);
		
		bookMenu = new JMenu();
		bookMenu.add(addBookAction);
		bookMenu.add(editBookAction);
		bookMenu.add(removeBookAction);
		menuBar.add(bookMenu);
		
		topicMenu = new JMenu();
		topicMenu.add(addTopicAction);
		topicMenu.add(editTopicAction);
		topicMenu.add(removeTopicAction);
		menuBar.add(topicMenu);
		
		languageMenu = new JMenu();
		languageMenu.add(enAction);
		languageMenu.add(ruAction);
		menuBar.add(languageMenu);
		this.setJMenuBar(menuBar);
		
		bookModel = new BookTableModel();
		bookTable = new JTable(bookModel);
		topicModel = new DefaultListModel();
		topicList = new JList(topicModel);
		
		booksPanel = new JPanel(new BorderLayout());
		booksPanel.add(new JScrollPane(bookTable));
		topicsPanel = new JPanel(new BorderLayout());
		topicsPanel.add(new JScrollPane(topicList));
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, booksPanel, topicsPanel);
		splitPane.setResizeWeight(0.5);
		this.getContentPane().add(splitPane);
		
		Locale locale = new Locale("en");
		ResourceBundle bundle = ResourceBundle.getBundle("resources", locale);
		setTitles(bundle);
		
		topicList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (!refresing) {
						refresh();
					}
				} catch (RemoteException ex) {
					JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
					        JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
	}
	
	/**
	 * Connects to the server.
	 */
	public void connect() {
		try {
			if (connectDialog.showConnectDialog()) {
				bookstore = connectDialog.getBookstore();
				refresh();
				setVisible(true);
			} else {
				System.exit(0);
			}
		} catch (RemoteException e) {
			disconnect(e);
		} catch (NumberFormatException e) {
			disconnect(e);
		} catch (NotBoundException e) {
			disconnect(e);
		}
	}
	
	private void disconnect(Exception e) {
		JOptionPane.showMessageDialog(connectDialog, errorMessage + e.getMessage(), error, JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
	
	private void refresh() throws RemoteException {
		refresing = true;
		
		ArrayList<Topic> oldSelectedTopics = new ArrayList<Topic>();
		Object[] selectedTopicsArray = topicList.getSelectedValues();
		for (Object element : selectedTopicsArray) {
			oldSelectedTopics.add((Topic) element);
		}
		
		topicModel.clear();
		List<Topic> topics = bookstore.getTopics();
		for (Topic t : topics) {
			topicModel.addElement(t);
		}
		
		List<Topic> newSelectedTopics = new ArrayList<Topic>();
		for (Topic t : oldSelectedTopics) {
			for (int i = 0; i < topicModel.getSize(); i++) {
				if (((Topic) topicModel.get(i)).getId() == t.getId()) {
					newSelectedTopics.add((Topic) topicModel.get(i));
					break;
				}
			}
		}
		
		int[] newSelectedTopicIndices = new int[newSelectedTopics.size()];
		int j = 0;
		for (Topic t : newSelectedTopics) {
			for (int i = 0; i < topicModel.getSize(); i++) {
				if (((Topic) topicModel.get(i)).getId() == t.getId()) {
					newSelectedTopicIndices[j++] = i;
					break;
				}
			}
		}
		topicList.setSelectedIndices(newSelectedTopicIndices);
		
		int[] oldSelectedBookIndices = bookTable.getSelectedRows();
		List<Book> oldSelectedBooks = new ArrayList<Book>();
		for (int oldSelectedBookIndice : oldSelectedBookIndices) {
			oldSelectedBooks.add(bookModel.getBookAt(oldSelectedBookIndice));
		}
		
		bookModel.clear();
		List<Book> books = bookstore.getBooks(newSelectedTopics);
		for (Book b : books) {
			bookModel.addBook(b);
		}
		
		ArrayList<Book> newSelectedBooks = new ArrayList<Book>();
		for (Book b : oldSelectedBooks) {
			for (int i = 0; i < bookModel.getRowCount(); i++) {
				if (bookModel.getBookAt(i).getId() == b.getId()) {
					newSelectedBooks.add(b);
					break;
				}
			}
		}
		
		for (Book b : newSelectedBooks) {
			for (int i = 0; i < bookModel.getRowCount(); i++) {
				if (bookModel.getBookAt(i).getId() == b.getId()) {
					bookTable.getSelectionModel().addSelectionInterval(i, i);
					break;
				}
			}
		}
		
		refresing = false;
	}
	
	private List<Topic> getTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		for (int i = 0; i < topicModel.size(); i++) {
			topics.add((Topic) topicModel.get(i));
		}
		return topics;
	}
	
	private final class RefreshAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				refresh();
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class ExitAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	private final class AddBookAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				refresh();
				bookDialog.setTopics(getTopics());
				if (bookDialog.showAddBookDialog()) {
					Book book = bookDialog.getBook();
					bookstore.addBook(book);
					refresh();
				}
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class EditBookAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				refresh();
				if (bookTable.getSelectedRow() != -1) {
					bookDialog.setTopics(getTopics());
					if (bookDialog.showEditBookDialog(bookModel.getBookAt(bookTable.getSelectedRow()))) {
						Book book = bookDialog.getBook();
						bookstore.editBook(book);
						refresh();
					}
				}
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class RemoveBookAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				refresh();
				if (bookTable.getSelectedRow() != -1) {
					bookstore.removeBook(bookModel.getBookAt(bookTable.getSelectedRow()).getId());
					refresh();
				}
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class AddTopicAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				refresh();
				if (topicDialog.showAddTopicDialog()) {
					Topic topic = topicDialog.getTopic();
					bookstore.addTopic(topic.getTitle());
					refresh();
				}
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class EditTopicAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// Capture the selected topic if any. Or may be ask for it.
			try {
				refresh();
				if (!topicList.isSelectionEmpty()) {
					if (topicDialog.showEditTopicDialog((Topic) topicList.getSelectedValue())) {
						Topic topic = topicDialog.getTopic();
						bookstore.editTopic(topic);
						refresh();
					}
				}
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class RemoveTopicAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				refresh();
				if (!topicList.isSelectionEmpty()) {
					Topic topic = (Topic) topicList.getSelectedValue();
					bookstore.removeTopic(topic.getId());
					refresh();
				}
			} catch (RemoteException ex) {
				JOptionPane.showMessageDialog(connectDialog, errorMessage + ex.getMessage(), error,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	private final class EnAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale("en"));
			setTitles(bundle);
		}
	}
	
	private final class RuAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale("ru"));
			setTitles(bundle);
		}
	}
	
	/**
	 * Runs the program.
	 * 
	 * @param args
	 *            - ignored.
	 */
	public static void main(String[] args) {
		BookstoreFrame bookstoreFrame = new BookstoreFrame();
		bookstoreFrame.connect();
	}
	
	private void setTitles(ResourceBundle bundle) {
		this.setTitle(bundle.getString("bookstore_title"));
		fileMenu.setText(bundle.getString("file"));
		bookMenu.setText(bundle.getString("book"));
		topicMenu.setText(bundle.getString("topic"));
		languageMenu.setText(bundle.getString("language"));
		booksPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("books_border")));
		topicsPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("topics_border")));
		errorMessage = bundle.getString("error_message");
		error = bundle.getString("error");
		refreshAction.putValue(Action.NAME, bundle.getString("refresh_action"));
		exitAction.putValue(Action.NAME, bundle.getString("exit_action"));
		addBookAction.putValue(Action.NAME, bundle.getString("add_action"));
		editBookAction.putValue(Action.NAME, bundle.getString("edit_action"));
		removeBookAction.putValue(Action.NAME, bundle.getString("remove_action"));
		addTopicAction.putValue(Action.NAME, bundle.getString("add_action"));
		editTopicAction.putValue(Action.NAME, bundle.getString("edit_action"));
		removeTopicAction.putValue(Action.NAME, bundle.getString("remove_action"));
		enAction.putValue(Action.NAME, bundle.getString("english"));
		ruAction.putValue(Action.NAME, bundle.getString("russian"));
		bookDialog.setTitles(bundle);
		bookModel.setTitles(bundle);
		connectDialog.setTitles(bundle);
		topicDialog.setTitles(bundle);
	}
}