package palyuhovich.speclab.bookstore;

import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

import javax.swing.*;

/**
 * Class extends <code>JDialog</code> which connects to server.
 * 
 * @author Yana Palyuhovich
 */
public class ConnectDialog extends JDialog {
	
	private JLabel addressLabel;
	private JLabel portLabel;
	private JTextField adressTextField;
	private JTextField portTextField;
	private JButton connectButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JPanel panel2;
	
	private boolean result;
	private ConnectAction connectAction;
	private CancelAction cancelAction;
	
	/**
	 * Constructs a dialog.
	 */
	public ConnectDialog() {
		this.setModal(true);
		this.setResizable(false);
		
		connectAction = new ConnectAction();
		cancelAction = new CancelAction();
		
		panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		
		addressLabel = new JLabel();
		portLabel = new JLabel();
		adressTextField = new JTextField(20);
		portTextField = new JTextField(20);
		adressTextField.setText("127.0.0.1");
		portTextField.setText("4444");
		panel1.add(addressLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(adressTextField, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(portLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		panel1.add(portTextField, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
		        GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		this.getContentPane().add(panel1, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		connectButton = new JButton(connectAction);
		cancelButton = new JButton(cancelAction);
		panel2.add(connectButton);
		panel2.add(cancelButton);
		this.getContentPane().add(panel2, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Shows the dialog.
	 * 
	 * @return <ul> <li><code>true</code> if the connection is successful.</li> <li><code>false</code> otherwise.</li>
	 *         </ul>
	 */
	public boolean showConnectDialog() {
		this.setVisible(true);
		return result;
	}
	
	private final class ConnectAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			result = true; // If the connection is successful.
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
	 * Creates and returns an object of <code>Bookstore</code>.
	 * 
	 * @return an object of <code>Bookstore</code>.
	 * @throws RemoteException
	 *             if the connection can't be created.
	 * @throws NumberFormatException
	 *             if port number is wrong.
	 * @throws NotBoundException
	 *             if the connection can't be created.
	 */
	public Bookstore getBookstore() throws NumberFormatException, RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		Registry registry;
		Bookstore bookstore;
		
		registry = LocateRegistry.getRegistry(adressTextField.getText(), Integer.parseInt(portTextField.getText()));
		bookstore = (Bookstore) registry.lookup("Bookstore");
		return bookstore;
	}
	
	public void setTitles(ResourceBundle bundle) {
		this.setTitle(bundle.getString("connect_title"));
		addressLabel.setText(bundle.getString("address"));
		portLabel.setText(bundle.getString("port"));
		connectAction.putValue(Action.NAME, bundle.getString("connect"));
		cancelAction.putValue(Action.NAME, bundle.getString("cancel"));
		this.pack();
	}
}