package palyuhovich.speclab.bookstore;

import java.io.*;
import java.util.*;

import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class BookstoreXML {
	
	private static final File XML_FILE = new File("bookstore.xml");
	private static final File SCHEMA_FILE = new File("bookstore.xsd");
	
	private DefaultHandler topicHandler;
	private DefaultHandler bookHandler;
	private SAXParser saxParser;
	private Document document;
	
	private List<Topic> topics = new ArrayList<Topic>();
	private List<Topic> topicsToGet = new ArrayList<Topic>();
	private List<Book> books = new ArrayList<Book>();
	private List<Integer> ids = new ArrayList<Integer>();
	
	private int topicId = -1;
	private int bookId = -1;
	private String title = "";
	private String authors = "";
	
	private boolean topicsDirty = true;
	private boolean isTopic = false;
	private boolean isTitle = false;
	private boolean isAuthors = false;
	private boolean isTopicId = false;
	
	public void setTopicsDirty(boolean d) {
		topicsDirty = d;
	}
	
	public boolean isTopicsDirty() {
		return topicsDirty;
	}
	
	public void createParsers() throws SAXException, IOException, ParserConfigurationException {
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		Schema schema = factory.newSchema(SCHEMA_FILE);
		Validator validator = schema.newValidator();
		SAXSource source1 = new SAXSource(new InputSource(new FileInputStream(XML_FILE)));
		validator.validate(source1);
		topicHandler = new SAXTopicHandler();
		bookHandler = new SAXBookHandler();
		SAXParserFactory SAXFactory = SAXParserFactory.newInstance();
		saxParser = SAXFactory.newSAXParser();
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		document = parser.parse(XML_FILE);
	}
	
	private void writeDocument() {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(XML_FILE);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private final class SAXTopicHandler extends DefaultHandler {
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (isTopic) {
				topics.add(new Topic(topicId, new String(ch, start, length).trim()));
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if ("topic".equals(qName)) isTopic = false;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if ("topic".equals(qName)) {
				isTopic = true;
				topicId = Integer.parseInt(attributes.getValue("topicId"));
			}
		}
	}
	
	private final class SAXBookHandler extends DefaultHandler {
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (isTitle) title = new String(ch, start, length).trim();
			if (isAuthors) authors = new String(ch, start, length).trim();
			if (isTopicId) ids.add(Integer.parseInt(new String(ch, start, length).trim()));
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if ("book".equals(qName)) {
				for (Topic t : topicsToGet) {
					if (!ids.contains(t.getId())) return;
				}
				List<Topic> ts = new ArrayList<Topic>();
				for (int i : ids) {
					for (Topic t : topics)
						if (t.getId() == i) ts.add(new Topic(i, t.getTitle()));
				}
				books.add(new Book(bookId, title, authors, ts));
			}
			if ("title".equals(qName)) isTitle = false;
			if ("authors".equals(qName)) isAuthors = false;
			if ("topicId".equals(qName)) isTopicId = false;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if ("book".equals(qName)) {
				bookId = Integer.parseInt(attributes.getValue("bookId"));
				title = "";
				authors = "";
				ids.clear();
			}
			if ("title".equals(qName)) isTitle = true;
			if ("authors".equals(qName)) isAuthors = true;
			if ("topicId".equals(qName)) isTopicId = true;
		}
	}
	
	public void addBook(Book book) {
		Node booksNode = document.getElementsByTagName("books").item(0);
		Node bookNextIdNode = booksNode.getAttributes().getNamedItem("nextId");
		int bookId = Integer.parseInt(bookNextIdNode.getNodeValue());
		bookNextIdNode.setNodeValue(String.valueOf(bookId + 1));
		
		Element bookElement = document.createElement("book");
		Attr bookIdNode = document.createAttribute("bookId");
		bookIdNode.setValue(String.valueOf(bookId));
		bookElement.getAttributes().setNamedItem(bookIdNode);
		
		Element titleBookElement = document.createElement("title");
		titleBookElement.setTextContent(book.getTitle());
		Element authorsBookElement = document.createElement("authors");
		authorsBookElement.setTextContent(book.getAuthors());
		Element relatedTopicsBookElement = document.createElement("relatedTopics");
		for (int i = 0; i < book.getTopics().size(); i++) {
			Element topicsIdBookElement = document.createElement("topicId");
			topicsIdBookElement.setTextContent(String.valueOf(book.getTopics().get(i).getId()));
			relatedTopicsBookElement.appendChild(topicsIdBookElement);
		}
		
		bookElement.appendChild(titleBookElement);
		bookElement.appendChild(authorsBookElement);
		bookElement.appendChild(relatedTopicsBookElement);
		
		booksNode.appendChild(bookElement);
		
		writeDocument();
	}
	
	public void editBook(Book book) {
		NodeList bookNodes = document.getElementsByTagName("book");
		getTopics();
		book.getTopics().retainAll(topics);
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Node bookNode = bookNodes.item(i);
			if (Integer.parseInt(bookNode.getAttributes().getNamedItem("bookId").getNodeValue()) == book.getId()) {
				NodeList childNodes = bookNode.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					if (childNodes.item(j).getNodeName().trim().equals("title"))
						childNodes.item(j).setTextContent(book.getTitle());
					else if (childNodes.item(j).getNodeName().trim().equals("authors"))
						childNodes.item(j).setTextContent(book.getAuthors());
					else if (childNodes.item(j).getNodeName().trim().equals("relatedTopics")) {
						bookNode.removeChild(childNodes.item(j));
						Element relatedTopicsBookElement = document.createElement("relatedTopics");
						for (int k = 0; k < book.getTopics().size(); k++) {
							Element topicsIdBookElement = document.createElement("topicId");
							topicsIdBookElement.setTextContent(String.valueOf(book.getTopics().get(k).getId()));
							relatedTopicsBookElement.appendChild(topicsIdBookElement);
						}
						bookNode.appendChild(relatedTopicsBookElement);
					}
				}
				writeDocument();
				break;
			}
		}
	}
	
	public void removeBook(int id) {
		NodeList bookNodes = document.getElementsByTagName("book");
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Node bookNode = bookNodes.item(i);
			if (Integer.parseInt(bookNode.getAttributes().getNamedItem("bookId").getNodeValue()) == id) {
				bookNode.getParentNode().removeChild(bookNode);
				break;
			}
		}
		
		writeDocument();
	}
	
	public void addTopic(String title) {
		Node topicsNode = document.getElementsByTagName("topics").item(0);
		Node topicNextIdNode = topicsNode.getAttributes().getNamedItem("nextId");
		int topicId = Integer.parseInt(topicNextIdNode.getNodeValue());
		topicNextIdNode.setNodeValue(String.valueOf(topicId + 1));
		
		Element topicElement = document.createElement("topic");
		Attr topicIdNode = document.createAttribute("topicId");
		topicIdNode.setValue(String.valueOf(topicId));
		topicElement.getAttributes().setNamedItem(topicIdNode);
		topicElement.setTextContent(title);
		
		topicsNode.appendChild(topicElement);
		
		writeDocument();
		setTopicsDirty(true);
	}
	
	public void editTopic(Topic topic) {
		NodeList topicNodes = document.getElementsByTagName("topic");
		for (int i = 0; i < topicNodes.getLength(); i++) {
			Node topicNode = topicNodes.item(i);
			if (Integer.parseInt(topicNode.getAttributes().getNamedItem("topicId").getNodeValue()) == topic.getId()) {
				topicNode.setTextContent(topic.getTitle());
				
				writeDocument();
				setTopicsDirty(true);
				break;
			}
		}
	}
	
	public void removeTopic(int id) {
		NodeList topicNodes = document.getElementsByTagName("topic");
		for (int i = 0; i < topicNodes.getLength(); i++) {
			Node topicNode = topicNodes.item(i);
			if (Integer.parseInt(topicNode.getAttributes().getNamedItem("topicId").getNodeValue()) == id) {
				topicNode.getParentNode().removeChild(topicNode);
				break;
			}
		}
		
		NodeList topicIdNodes = document.getElementsByTagName("topicId");
		for (int i = 0; i < topicIdNodes.getLength(); i++) {
			Node topicIdNode = topicIdNodes.item(i);
			if (Integer.parseInt(topicIdNode.getTextContent()) == id) {
				topicIdNode.getParentNode().removeChild(topicIdNode);
			}
		}
		
		writeDocument();
		setTopicsDirty(true);
	}
	
	public List<Book> getBooks(List<Topic> topics) {
		try {
			getTopics();
			topics.retainAll(this.topics);
			topicsToGet = topics;
			books.clear();
			saxParser.parse(XML_FILE, bookHandler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Topic> getTopics() {
		if (isTopicsDirty()) {
			try {
				topics.clear();
				saxParser.parse(XML_FILE, topicHandler);
				setTopicsDirty(false);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return topics;
	}
}
