package palyuhovich.speclab.bookstore;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * Class <code>BookDialog</code> extends <code>JDialog</code> for adding or editing book.
 * 
 * @author Yana Palyuhovich
 */
public class BookDialog extends JDialog {
	
	private JLabel titleLabel;
	private JLabel authorsLabel;
	private JLabel topicLabel;
	private JTextField titleTextField;
	private JTextField authorsTextField;
	private DefaultListModel topicListModel;
	private JList topicList;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JPanel panel2;
	
	private int id;
	private boolean result;
	private String addBookTitle;
	private String editBookTitle;
	private OkAction okAction;
	private CancelAction cancelAction;
	
	/**
	 * Constructs a dialog.
	 */
	public BookDialog() {
		id = -1;
		addBookTitle = "";
		editBookTitle = "";
		okAction = new OkAction();
		cancelAction = new CancelAction();
		
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		
		topicListModel = new DefaultListModel();
		
		titleLabel = new JLabel();
		authorsLabel = new JLabel();
		topicLabel = new JLabel();
		titleTextField = new JTextField(20);
		authorsTextField = new JTextField(20);
		topicList = new JList(topicListModel);
		topicList.setMaximumSize(new Dimension(200, 300));
		panel1.add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(titleTextField, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(authorsLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(authorsTextField, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(topicLabel, new GridBagConstraints(0, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(new JScrollPane(topicList), new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		this.getContentPane().add(panel1, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		okButton = new JButton(okAction);
		cancelButton = new JButton(cancelAction);
		panel2.add(okButton);
		panel2.add(cancelButton);
		this.getContentPane().add(panel2, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Sets topics of the dialog.
	 * 
	 * @param topics
	 *            - List of topics which are set.
	 */
	public void setTopics(List<Topic> topics) {
		topicListModel.clear();
		for (Topic t : topics) {
			topicListModel.addElement(t);
		}
	}
	
	/**
	 * Shows the dialog.
	 * 
	 * @return <ul> <li><code>true</code> if button "Ok" is selected.</li> <li><code>false</code> otherwise.</li> </ul>
	 */
	public boolean showAddBookDialog() {
		id = -1;
		this.setTitle(addBookTitle);
		titleTextField.setText("");
		authorsTextField.setText("");
		this.setVisible(true);
		return result;
	}
	
	/**
	 * Shows the dialog with the book is edited.
	 * 
	 * @param book
	 *            - The book is edited.
	 * @return <ul> <li><code>true</code> if button "Ok" is selected.</li> <li><code>false</code> otherwise.</li> </ul>
	 */
	public boolean showEditBookDialog(Book book) {
		this.setTitle(editBookTitle);
		id = book.getId();
		titleTextField.setText(book.getTitle());
		authorsTextField.setText(book.getAuthors());
		int[] selectedTopicIndices = new int[book.getTopics().size()];
		for (int i = 0; i < selectedTopicIndices.length; ++i) {
			Topic t = book.getTopics().get(i);
			int index = topicListModel.indexOf(t);
			selectedTopicIndices[i] = index;
		}
		topicList.setSelectedIndices(selectedTopicIndices);
		this.setVisible(true);
		return result;
	}
	
	private final class OkAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			result = true;
			setVisible(false);
		}
	}
	
	private final class CancelAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			result = false;
			setVisible(false);
		}
	}
	
	/**
	 * Creates and returns a book with parameters in the dialog.
	 * 
	 * @return A new book.
	 */
	public Book getBook() {
		List<Topic> topics = new ArrayList<Topic>();
		int[] selestedIndices = topicList.getSelectedIndices();
		for (int selestedIndice : selestedIndices) {
			topics.add((Topic) topicListModel.getElementAt(selestedIndice));
		}
		return new Book(id, titleTextField.getText(), authorsTextField.getText(), topics);
	}
	
	public void setTitles(ResourceBundle bundle) {
		titleLabel.setText(bundle.getString("book_title"));
		authorsLabel.setText(bundle.getString("book_authors"));
		topicLabel.setText(bundle.getString("book_topics"));
		addBookTitle = bundle.getString("add_book_title");
		editBookTitle = bundle.getString("edit_book_title");
		okAction.putValue(Action.NAME, bundle.getString("ok"));
		cancelAction.putValue(Action.NAME, bundle.getString("cancel"));
		this.pack();
	}
}