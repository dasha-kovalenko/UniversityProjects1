package palyuhovich.speclab.bookstore;

import java.io.*;
import java.rmi.registry.*;
import java.rmi.server.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

/**
 * The main class of program.
 * 
 * @author Yana Palyuhovich
 */
public class Server {
	
	/**
	 * Runs the program.
	 * 
	 * @param args
	 *            - ignored.
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		BookstoreClass bookstoreClass = new BookstoreClass();
		BookstoreXML bookstoreXML = new BookstoreXML();
		bookstoreXML.createParsers();
		bookstoreClass.setBookstoreXML(bookstoreXML);
		Bookstore remoteBookstore = (Bookstore) UnicastRemoteObject.exportObject(bookstoreClass, 0);
		Registry registry = LocateRegistry.createRegistry(4444);
		registry.rebind("Bookstore", remoteBookstore);
	}
}