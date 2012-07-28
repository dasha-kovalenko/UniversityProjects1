package palyuhovich.speclab.bookstore;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * Class <code>TopicDialog</code> extends <code>JDialog</code> for adding or editing topic.
 * 
 * @author Yana Palyuhovich
 */
public class TopicDialog extends JDialog {
	
	private JLabel titleLabel;
	private JTextField titleField;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JPanel panel2;
	
	private int id;
	private boolean result;
	private String addTopicTitle;
	private String editTopicTitle;
	private OkAction okAction;
	private CancelAction cancelAction;
	
	/**
	 * Constructs a dialog.
	 */
	public TopicDialog() {
		id = -1;
		addTopicTitle = "";
		editTopicTitle = "";
		okAction = new OkAction();
		cancelAction = new CancelAction();
		
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		panel1 = new JPanel();
		titleLabel = new JLabel();
		titleField = new JTextField(20);
		panel1.add(titleLabel, BorderLayout.NORTH);
		panel1.add(titleField, BorderLayout.SOUTH);
		this.getContentPane().add(panel1);
		
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
	 * Shows the dialog.
	 * 
	 * @return <ul> <li><code>true</code> if button "Ok" is selected.</li> <li><code>false</code> otherwise.</li> </ul>
	 */
	public boolean showAddTopicDialog() {
		id = -1;
		this.setTitle(addTopicTitle);
		titleField.setText("");
		this.setVisible(true);
		return result;
	}
	
	/**
	 * Shows the dialog with the topic is edited.
	 * 
	 * @param topic
	 *            - The topic is edited.
	 * @return <ul> <li><code>true</code> if button "Ok" is selected.</li> <li><code>false</code> otherwise.</li> </ul>
	 */
	public boolean showEditTopicDialog(Topic topic) {
		this.setTitle(editTopicTitle);
		id = topic.getId();
		titleField.setText(topic.getTitle());
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
	 * Creates and returns a book with title in the dialog.
	 * 
	 * @return A new topic.
	 */
	public Topic getTopic() {
		return new Topic(id, titleField.getText());
	}
	
	public void setTitles(ResourceBundle bundle) {
		titleLabel.setText(bundle.getString("topic_title"));
		addTopicTitle = bundle.getString("add_topic_title");
		editTopicTitle = bundle.getString("edit_topic_title");
		okAction.putValue(Action.NAME, bundle.getString("ok"));
		cancelAction.putValue(Action.NAME, bundle.getString("cancel"));
		this.pack();
	}
}