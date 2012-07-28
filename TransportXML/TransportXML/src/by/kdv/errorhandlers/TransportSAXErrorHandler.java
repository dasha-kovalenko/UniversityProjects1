package by.kdv.errorhandlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TransportSAXErrorHandler implements ErrorHandler {

	@Override
	public void error(SAXParseException exc) throws SAXException {
		try {
			FileWriter fw = new FileWriter("error.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Error: " + exc.getMessage() + "\n");
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			throw new SAXException("Could not write to log file", e);
		}
	}

	@Override
	public void fatalError(SAXParseException exc) throws SAXException {
		try {
			FileWriter fw = new FileWriter("error.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Fatal Error: " + exc.getMessage() + "\n");
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			throw new SAXException("Could not write to log file", e);
		}
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		try {
			FileWriter fw = new FileWriter("error.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Warning: " + exception.getMessage() + "\n");
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			throw new SAXException("Could not write to log file", e);
		}
	}

}
