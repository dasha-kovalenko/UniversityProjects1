package by.kdv.serverrmi;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class ServerFrame extends JFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private FootballServer footballdb;
		
		public ServerFrame(int port, String xmlFileName, String xsdFileName) throws RemoteException,
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
			this.setFootballdb(footballdb);
			initComponents();
			initProperties();
			Schema schema;
			try {
				schema = loadSchema(xsdFileName);
				validateXml(schema, xmlFileName);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Invalid XML-file!");
				System.exit(0);
			}
			

		}
	/*
		синтаксическая корректность (well-formed): то есть соблюдение всех синтаксических правил XML;
		действительность (valid): то есть данные соответствуют некоторому набору правил, определённых пользо
		вателем; правила определяют структуру и формат данных в XML. Валидность XML документа определяется 
		наличием DTD или XML-схемы XSD и соблюдением правил, которые там приведены.
	*/
		private Schema loadSchema(String name) throws SAXException {
			Schema schema = null;
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(language);
			schema = factory.newSchema(new File(name));
			return schema;
		}
		private void validateXml(Schema schema, String xmlName) {
			Validator validator = schema.newValidator();
			SAXSource source;
			try {
				source = new SAXSource(new InputSource(new FileInputStream(xmlName)));
				validator.validate(source);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				//e.printStackTrace();
				System.out.println("!");
				JOptionPane.showMessageDialog(this, "Invalid XML-file!");
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		
		public FootballServer getFootballdb() {
			return footballdb;
		}
		public void setFootballdb(FootballServer footballdb) {
			this.footballdb = footballdb;
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
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}
			
		}
		
		
}
