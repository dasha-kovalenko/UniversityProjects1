package by.aig.serverrmi;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import by.aig.common.Candidate;

public class CandidateSaxParser extends DefaultHandler {

	private Candidate candidate;
	private List<Candidate> resultList;
	private String currentElement;

	private boolean male;
	private int minAge;
	private int maxAge;
	private String country;
	private String city;

	private boolean accepted;
	private boolean countryAccepted;
	private boolean cityAccepted;

	public CandidateSaxParser(boolean male, int minAge, int maxAge,
			String country, String city) {
		this.male = male;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.country = country;
		this.city = city;
	}

	public List<Candidate> getResultList() {
		return resultList;
	}

	@Override
	public void startDocument() throws SAXException {
		resultList = new ArrayList<Candidate>();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		currentElement = localName;
		if ("candidate".equals(currentElement)) {
			candidate = new Candidate();
			accepted = true;
			countryAccepted = country == null ? true : false;
			cityAccepted = city == null ? true : false;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		currentElement = "";
		if ("candidate".equals(localName) && accepted && countryAccepted
				&& cityAccepted)
			resultList.add(candidate);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (!accepted)
			return;
		String element = new String(ch, start, length);
		if (currentElement.equals("id")) {
			candidate.setId(new Integer(element));
		} else if (currentElement.equals("age")) {
			int ageElement = Integer.parseInt(element);
			if (ageElement >= minAge && ageElement <= maxAge)
				candidate.setAge(ageElement);
			else
				accepted = false;
		} else if (currentElement.equals("name")) {
			candidate.setName(element);
		} else if (currentElement.equals("male")) {
			boolean maleElement = Boolean.parseBoolean(element);
			if (maleElement == male)
				candidate.setMale(maleElement);
			else
				accepted = false;
		} else if (currentElement.equals("country")) {
			String countryElement = element;
			if (country == null || countryElement.equals(country)) {
				candidate.setCountry(countryElement);
				countryAccepted = true;
			} else
				accepted = false;
		} else if (currentElement.equals("city")) {
			String cityElement = element;
			if (city == null || cityElement.equals(city)) {
				candidate.setCity(cityElement);
				cityAccepted = true;
			} else
				accepted = false;
		} else if (currentElement.equals("phone")) {
			candidate.setPhone(new String(ch, start, length));
		}
	}

}