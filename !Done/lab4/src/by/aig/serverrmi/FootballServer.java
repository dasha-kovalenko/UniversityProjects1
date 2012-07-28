package by.aig.serverrmi;

import java.io.File;
import java.io.FileInputStream;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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

import by.aig.common.FootballDataBase;
import by.aig.common.Match;


public class FootballServer extends UnicastRemoteObject implements FootballDataBase{
	private String xmlFileName;
	private String xsdFileName;
	
	//!!!!!
	private Namespace tns = Namespace.getNamespace("tns",
	"http://www.clientmatch.by.aig/Match");
	
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
	public void deleteMatch(String team1, String team2, String count, Date date)
			throws RemoteException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlFileName));
			List children = document.getRootElement().getChildren();
			Element temp = null;
			/*for (Object el : children) {
				if (!((Element) el).getName().equals("temp")
						&& ((Element) el).getChild("id", tns).getText()
								.equals(String.valueOf(id))) {
					temp = (Element) el;
					break;
				}
			}*/
			if (temp != null) {
				document.getRootElement().indexOf(temp);
				System.out.println(document.getRootElement().indexOf(temp));
				document.getRootElement().removeContent(
						document.getRootElement().indexOf(temp));
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
		// TODO Auto-generated method stub
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlFileName));
			int id = getDocumentMaximumId(document);
			//System.out.println(document);
			Match match = new Match(++id, team1, team2, count, date);
			Element xmlMatchElement = getXmlMatchElement(match);
			//System.out.println(xmlMatchElement);
			document.getRootElement().addContent(xmlMatchElement);
			//System.out.println(document.getRootElement().getChildren());
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
		List<Element> children = document.getRootElement().getChildren();
		int temp;
		for (Element el : children) {
			//System.out.println(el.getChild("id", tns));
			if (!((Element) el).getName().equals("temp")) {
				temp = Integer.parseInt(el.getChild("id", tns).getText());
				if (temp > id)
					id = temp;
			}
		}
		return id;
	}
	
	@Override
	public List<Match> selectMatches(String team, Date startDate, Date endDate) throws RemoteException {
		// TODO Auto-generated method stub
		List<Match> resultList = new LinkedList<Match>();
		System.out.println("q!");
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			SaxParser msp = new SaxParser(team, startDate, endDate);
			reader.setContentHandler(msp);
			Schema schema = loadSchema(xsdFileName);
			validateXml(schema, xmlFileName);
			reader.parse(xmlFileName);
			resultList = msp.getResList();
			System.out.println(resultList.size()+" " +resultList.toString());

		} catch (SAXException e) {
			e.printStackTrace();
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
		return resultList;
	}

	private void validateXml(Schema schema, String xmlName) throws SAXException, IOException {
		Validator validator = schema.newValidator();
		SAXSource source = new SAXSource(new InputSource(new FileInputStream(xmlName)));
		validator.validate(source);
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
	public List<Match> showDataBase() throws RemoteException {
		// TODO Auto-generated method stub
		List<Match> list = new ArrayList<Match>();
		/*try {
			//System.out.println("q!");
			XMLReader reader = XMLReaderFactory.createXMLReader();
			SaxParser msp = new SaxParser();
			reader.setContentHandler(msp);
			reader.parse(xmlFileName);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			SaxParser saxp = new SaxParser();
			parser.parse(new File(xmlFileName), saxp);
			list = saxp.getResList();
			System.out.println(list.toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		return list;
	}


}
