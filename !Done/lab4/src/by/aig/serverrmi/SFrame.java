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


public class SFrame extends JFrame {

		private FootballServer footballdb;
		
		public SFrame(int port, String xmlFileName, String xsdFileName) throws RemoteException,
																					InstantiationException, 
																					IllegalAccessException,
																					ClassNotFoundException, 
																					SQLException, 
																					MalformedURLException {
			System.setProperty("java.security.policy",
			PolicyFileLocator.getLocationOfPolicyFile());
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
			LocateRegistry.createRegistry(port);
			FootballServer footballdb = new FootballServer(xmlFileName, xsdFileName);
			Naming.rebind("server.footballdb", footballdb);
			this.footballdb = footballdb;
			initComponents();
			initProperties();
		}

		private void initProperties() {
			setSize(320, 240);
			setVisible(true);
			setTitle("Football");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
		}

		private void initComponents() {
			JTextField field = new JTextField("server.RMI Server is ready.");
			field.setEditable(false);
			add(field);
			addWindowListener(new WindowListener());
			
		}
		
		
		private class WindowListener implements java.awt.event.WindowListener {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					Naming.unbind("server.footballdb");
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
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		
}
