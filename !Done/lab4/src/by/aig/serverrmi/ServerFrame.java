package by.aig.serverrmi;

import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ServerFrame extends JFrame {

	private MeetingDAO meetingDAO;

	public ServerFrame(int port, String xmlFileName, String xsdFileName) throws RemoteException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, MalformedURLException {
		System.setProperty("java.security.policy",
				PolicyFileLocator.getLocationOfPolicyFile());
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		LocateRegistry.createRegistry(port);
		MeetingDAO meetingDAO = new MeetingDAO(xmlFileName, xsdFileName);
		Naming.rebind("server.meetingDAO", meetingDAO);
		this.meetingDAO = meetingDAO;
		initComponents();
		initProperties();
	}

	private void initComponents() {
		JTextField field = new JTextField("server.RMI Server is ready.");
		field.setEditable(false);
		add(field);
		addWindowListener(new WindowListener());
	}

	private void initProperties() {
		setSize(320, 240);
		setVisible(true);
		setTitle("Сервер");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private class WindowListener implements java.awt.event.WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {

		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {
			try {
				Naming.unbind("server.meetingDAO");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				e1.printStackTrace();
			}
			System.exit(DISPOSE_ON_CLOSE);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {

		}

		@Override
		public void windowOpened(WindowEvent e) {

		}

	}
}
