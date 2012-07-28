package by.kdv.serverrmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


import by.kdv.common.FootballDataBase;
import by.kdv.common.Match;


public class FootballServer extends UnicastRemoteObject implements FootballDataBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xmlFileName;
	private String xsdFileName;
	
	//!!!!!
	private Namespace tns = Namespace.getNamespace("tns", "http://www.clientmatch.by.kdv/Match");
	
	public FootballServer() throws RemoteException, InstantiationException,
								   IllegalAccessException, ClassNotFoundException, SQLException {
		super(Registry.REGISTRY_PORT);
	}
	
	public FootballServer(String xmlFileName, String xsdFileName) throws 
																	RemoteException, 
																	InstantiationException,
																	IllegalAccessException, 
																	ClassNotFoundException, 
																	SQLException {
		super(Registry.REGISTRY_PORT);
		this.xmlFileName = xmlFileName;
		this.xsdFileName = xsdFileName;
}

	@Override
	public void deleteMatch(int id)
			throws RemoteException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlFileName));
			@SuppressWarnings("unchecked")
			List<Element> children = document.getRootElement().getChildren();
			Element temp = null;
			for (Object el : children) {
				if (!((Element) el).getName().equals("temp")
						&& ((Element) el).getChild("id", tns).getText().equals(String.valueOf(id))) {
					temp = (Element) el;
					break;
				}
			}
			if (temp != null) {
				document.getRootElement().indexOf(temp);
//				System.out.println(document.getRootElement().indexOf(temp));
				document.getRootElement().removeContent(document.getRootElement().indexOf(temp));
				XMLOutputter outputter = new XMLOutputter();
				outputter.setFormat(Format.getPrettyFormat());
				FileWriter writer = new FileWriter(xmlFileName);
				outputter.output(document, writer);
				outputter.output(document, System.out);
				writer.close();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
		
	}
	
	public Match getMatch(int id) throws RemoteException {
		return null;
	}


	public void updateMatch(Match match) throws RemoteException {
	}


	@Override
	public void insertMatch(String team1, String team2, String count, Date date)
			throws RemoteException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlFileName));
			int id = getDocumentMaximumId(document);
			Match match = new Match(++id, team1, team2, count, date);
			Element xmlMatchElement = getXmlMatchElement(match);
			document.getRootElement().addContent(xmlMatchElement);
			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
			FileWriter writer = new FileWriter(xmlFileName);
			outputter.output(document, writer);
			writer.close();
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
		
	}

	private Element getXmlMatchElement(Match match) {
		Element temp = new Element("match", tns);
		temp.addContent(new Element("id", tns).addContent(String
				.valueOf(match.getId())));
		temp.addContent(new Element("team1", tns).addContent(String
				.valueOf(match.getTeam1())));
		temp.addContent(new Element("team2", tns).addContent(String
				.valueOf(match.getTeam2())));
		temp.addContent(new Element("count", tns).addContent(String
				.valueOf(match.getCount())));
		temp.addContent(new Element("matchdate", tns).addContent(String
				.valueOf(match.getMatchdate())));
		return temp;
	}

	private int getDocumentMaximumId(Document document) {
		int id = 0;
		@SuppressWarnings("unchecked")
		List<Element> children = document.getRootElement().getChildren();
		int temp;
		for (Element el : children) {
			if (!((Element) el).getName().equals("temp")) {
				temp = Integer.parseInt(el.getChild("id", tns).getText());
				if (temp > id)
					id = temp;
			}
		}
		return id;
	}
	
	@Override
	public List<Match> selectMatches(/*JFrame f , */String team, Date startDate, Date endDate) throws RemoteException {
		List<Match> resultList = new LinkedList<Match>();
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			SaxParser msp = new SaxParser(team, startDate, endDate);
			reader.setContentHandler(msp);
			Schema schema = loadSchema(xsdFileName);
			validateXml(/*f,*/schema, xmlFileName);
			reader.parse(xmlFileName);
			resultList = msp.getResList();
		} catch (SAXException e) {
			e.printStackTrace();
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
		return resultList;
	}

	private void validateXml(/*JFrame frame,*/ Schema schema, String xmlName) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Schema loadSchema(String name) throws SAXException {
		Schema schema = null;
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		schema = factory.newSchema(new File(name));
		return schema;
	}

	//SAX - это событийный парсер для XML, т.е. он последовательно читает и рабирает данные 
	//из входного потока (это может быть файл, сетевое соединение, или любой другой InputStream). 
	//Когда парсер находит структурный элемент (открывающий тег, закрывающий тег, и т.п.), он 
	//оповещает об этом слушателя, и передает ему в качестве параметра найденый элемент. Слушатель 
	//делает все необходимые операции для данного элемента.
	@Override
	public List<Match> showDataBase(/*JFrame frame*/) throws RemoteException {
		List<Match> list = new ArrayList<Match>();
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			SaxParser msp = new SaxParser("any", Date.valueOf("2010-01-01"), Date.valueOf("2012-12-31"));
			reader.setContentHandler(msp);
			Schema schema = loadSchema(xsdFileName);
			validateXml(/*frame,*/schema, xmlFileName);
			reader.parse(xmlFileName);
			list = msp.getResList();

//			System.out.println(list.toString());
		} catch (SAXException e) {
			//e.printStackTrace();
			System.out.println("!");
			//System.exit(0);
			//System.err.println("Invalid XML!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list;
	}


}
