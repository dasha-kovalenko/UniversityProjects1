package by.aig.clientrmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.aig.common.Candidate;
import by.aig.common.Match;
import by.aig.serverrmi.CandidateSaxParser;
import by.aig.serverrmi.SaxParser;

public class ClientRunner {

/*	private static void validateXml(Schema schema, String xmlName)
			throws SAXException, IOException {
		Validator validator = schema.newValidator();
		SAXSource source = new SAXSource(new InputSource(new FileInputStream(
				xmlName)));
		validator.validate(source);
	}

	private static Schema loadSchema(String name) throws SAXException {
		Schema schema = null;
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		schema = factory.newSchema(new File(name));
		return schema;
	}*/
	public static void main(String[] args) {
		ClientFrame clientFrame = new ClientFrame("localhost","1099");
	}
}
		/*List<Match> lastResult = new LinkedList<Match>();
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			String s = new String("2012-01-01");
			Date st = Date.valueOf(s);
			String ss =  new String("2012-03-29");
			Date end = Date.valueOf(ss);
			
			SaxParser csp = new SaxParser("manchester-united",st,end);
			reader.setContentHandler(csp);
			Schema schema = loadSchema("Match.xsd");
			validateXml(schema, "Match.xml");
			reader.parse("Match.xml");
			lastResult = csp.getResList();
			System.out.println(lastResult.toString());
		} catch (SAXException e) {
			e.printStackTrace();
			try {
				throw new RemoteException();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				throw new RemoteException();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		
	}
*/
