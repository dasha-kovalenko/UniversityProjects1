package by.kdv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.kdv.XMLparsers.TransportDOMHandler;
import by.kdv.XMLparsers.TransportSAXHandler;
import by.kdv.XMLparsers.TransportStAXHandler;
import by.kdv.errorhandlers.TransportSAXErrorHandler;
import by.kdv.transport.Transport;

public class Controller {

	/**
	 * @param args
	 * @throws SAXException
	 */
	public static void main(String[] args) {
		//Валидация xml о xsd
		try {
			Schema schema = loadSchema("TrXMLSchema.xsd");
			validateXml(schema, "TrXMLSchema3.xml");
		} catch (SAXException e1) {
			System.out.println("Невалидный xml-файл.");
			//e1.printStackTrace();
			System.exit(0);
		}
		
		
		try {
			// Создаем экземпляр для парсинга
			XMLReader reader = XMLReaderFactory.createXMLReader();

			// Создаем ContentHandler
			TransportSAXHandler transportHandler = new TransportSAXHandler("A", "B");

			// Регистрируем content handler
			reader.setContentHandler(transportHandler);
			reader.setErrorHandler(new TransportSAXErrorHandler());

			// Разбираем InputSource
			InputSource inputSource = new InputSource("TrXMLSchema3.xml");
			reader.parse(inputSource);
			System.out.println("Sax handler:");
			for (Transport tr : transportHandler.getTransportList()) {
				System.out.println(tr);
				System.out.println("Price: " + tr.countPrice() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		TransportDOMHandler domHandler = new TransportDOMHandler(
				"TrXMLSchema3.xml");
		System.out.println("Dom handler:");
		for (Transport tr : domHandler.getTransportListByRoute("A", "B")) {
			System.out.println(tr);
			System.out.println("Price: " + tr.countPrice() + "\n");
		}
		
		TransportStAXHandler staxHandler = new TransportStAXHandler(
				"TrXMLSchema3.xml");
		System.out.println("Stax handler:");
		for (Transport tr : staxHandler.getTransportListByRoute("A", "B")) {
			System.out.println(tr);
			System.out.println("Price: " + tr.countPrice() + "\n");
		}

	}
	
	private static Schema loadSchema(String name) throws SAXException {
		Schema schema = null;
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		schema = factory.newSchema(new File(name));
		return schema;
	}
	private static void validateXml(Schema schema, String xmlName) throws SAXException {
		Validator validator = schema.newValidator();
		SAXSource source;
		try {
			source = new SAXSource(new InputSource(new FileInputStream(xmlName)));
			validator.validate(source);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
