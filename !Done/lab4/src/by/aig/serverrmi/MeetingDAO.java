package by.aig.serverrmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
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

import by.aig.common.Candidate;
import by.aig.common.IMeetingDAO;

public class MeetingDAO extends UnicastRemoteObject implements IMeetingDAO {

	private String xmlFileName;
	private String xsdFileName;

	private Namespace tns = Namespace.getNamespace("tns",
			"http://www.clientcandidate.aig.by/Candidate");

	public MeetingDAO() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		super(Registry.REGISTRY_PORT);
	}

	public MeetingDAO(String xmlFileName, String xsdFileName)
			throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		super(Registry.REGISTRY_PORT);
		this.xmlFileName = xmlFileName;
		this.xsdFileName = xsdFileName;
	}

	@Override
	public List<Candidate> selectCandidates(boolean male, int minAge,
			int maxAge, String country, String city) throws RemoteException {
		List<Candidate> lastResult = new LinkedList<Candidate>();
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			CandidateSaxParser csp = new CandidateSaxParser(male, minAge,
					maxAge, country, city);
			reader.setContentHandler(csp);
			Schema schema = loadSchema(xsdFileName);
			validateXml(schema, xmlFileName);
			reader.parse(xmlFileName);
			lastResult = csp.getResultList();
		} catch (SAXException e) {
			e.printStackTrace();
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
		return lastResult;
	}

	@Override
	public void insertCandidate(boolean male, String name, int age,
			String country, String city, String phone) throws RemoteException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlFileName));
			int id = getDocumentMaximumId(document);
			System.out.println(document);
			Candidate candidate = new Candidate(++id, age, name, male, country,
					city, phone);
			Element xmlCandidateElement = getXmlCandidateElement(candidate);
			System.out.println(xmlCandidateElement);
			document.getRootElement().addContent(xmlCandidateElement);
			System.out.println(document.getRootElement().getChildren());
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

	@Override
	public void deleteCandidate(int id) throws RemoteException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlFileName));
			List children = document.getRootElement().getChildren();
			Element temp = null;
			for (Object el : children) {
				if (!((Element) el).getName().equals("temp")
						&& ((Element) el).getChild("id", tns).getText()
								.equals(String.valueOf(id))) {
					temp = (Element) el;
					break;
				}
			}
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

	@Override
	public Candidate getCandidate(int id) throws RemoteException {
		return null;
	}

	@Override
	public void updateCandidate(Candidate candidate) throws RemoteException {
	}

	private int getDocumentMaximumId(Document document) {
		int id = 0;
		List<Element> children = document.getRootElement().getChildren();
		int temp;
		for (Element el : children) {
			System.out.println(el.getChild("id", tns));
			if (!((Element) el).getName().equals("temp")) {
				temp = Integer.parseInt(el.getChild("id", tns).getText());
				if (temp > id)
					id = temp;
			}
		}
		return id;
	}

	private Element getXmlCandidateElement(Candidate candidate) {
		Element temp = new Element("candidate", tns);
		temp.addContent(new Element("id", tns).addContent(String
				.valueOf(candidate.getId())));
		temp.addContent(new Element("age", tns).addContent(String
				.valueOf(candidate.getAge())));
		temp.addContent(new Element("name", tns).addContent(String
				.valueOf(candidate.getName())));
		temp.addContent(new Element("male", tns).addContent(String
				.valueOf(candidate.isMale())));
		if (candidate.getCountry() != null)
			temp.addContent(new Element("country", tns).addContent(String
					.valueOf(candidate.getCountry())));
		if (candidate.getCity() != null)
			temp.addContent(new Element("city", tns).addContent(String
					.valueOf(candidate.getCity())));
		temp.addContent(new Element("phone", tns).addContent(String
				.valueOf(candidate.getPhone())));
		return temp;
	}

	private void validateXml(Schema schema, String xmlName)
			throws SAXException, IOException {
		Validator validator = schema.newValidator();
		SAXSource source = new SAXSource(new InputSource(new FileInputStream(
				xmlName)));
		validator.validate(source);
	}

	private Schema loadSchema(String name) throws SAXException {
		Schema schema = null;
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		schema = factory.newSchema(new File(name));
		return schema;
	}

}
