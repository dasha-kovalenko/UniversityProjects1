package by.kdv.XMLparsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import by.kdv.factory.TransportFactory;
import by.kdv.transport.Bus;
import by.kdv.transport.Transport;
import by.kdv.transport.Trolleybus;

public class TransportSAXHandler implements ContentHandler {

	private Transport transport;
	private List<Transport> transportList;
	private String current;

	private String startPoint;
	private String endPoint;

	private boolean isRightTransport;

	public TransportSAXHandler(String start, String end) {
		super();
		this.startPoint = start;
		this.endPoint = end;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (!isRightTransport)
			return;
		String element = new String(ch, start, length);
		if (current.equals("number")) {
			transport.setNumber(new Integer(element));

		} else if (current.equals("start")) {
			try {
				transport.setStart(DateFormat.getTimeInstance().parse(element));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (current.equals("finish")) {
			try {
				transport
						.setFinish(DateFormat.getTimeInstance().parse(element));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (current.equals("route")) {
			String route = new String(element);
			transport.setRoute(route);
			if (!isRightRoute(route))
				isRightTransport = false;

		} else if (current.equals("price")) {
			transport.setValue(new Integer(element));

		} else if (current.equals("interval")) {
			transport.setInterval(new Integer(element));

		} else if (current.equals("capacity")) {
			transport.setCapacity(new Integer(element));

		} else if (current.equals("amountOfFuel")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(transport.getStart());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(transport.getFinish());

			Transport t1 = TransportFactory.getClassFromFactory("bus",
					transport.getCapacity(), transport.getNumber(),
					cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
					cal1.get(Calendar.HOUR), cal1.get(Calendar.MINUTE),
					transport.getRoute(), transport.getValue(),
					transport.getInterval());
			((Bus) t1).setAmountOfFuel(new Integer(element));

			transport = t1;

		} else if (current.equals("energy")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(transport.getStart());
			// System.out.println(cal.get(Calendar.HOUR));
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(transport.getFinish());
			Transport t2 = TransportFactory.getClassFromFactory("trolleybus",
					transport.getCapacity(), transport.getNumber(),
					cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
					cal1.get(Calendar.HOUR), cal1.get(Calendar.MINUTE),
					transport.getRoute(), transport.getValue(),
					transport.getInterval());
			((Trolleybus) t2).setEnergy(new Integer(element));
			// System.out.println(t2.toString());
			
			transport = t2;
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		current = localName;
		if (("Bus".equals(current) || "Trolleybus".equals(current)) && isRightTransport) {
			transportList.add(transport);
		}
		current = "";
	}

	public String getRightRoute(String route) {
		String foundRoute = "";
		if (route.contains(startPoint) && route.contains(endPoint)) {
			int start = route.indexOf(startPoint);
			int end = route.indexOf(endPoint);
			StringBuilder tmp1 = new StringBuilder();
			if (start < end) {
				tmp1.append(route.substring(start, end));
				tmp1.append(endPoint);
			} else {
				tmp1.append(route.substring(end, start));
				tmp1.append(startPoint);
				tmp1.reverse();
			}
			foundRoute = tmp1.toString();
			// System.out.println(transport.getValue());
			// int value = countValue(foundRoute,transport.getValue());
			// trMap.put(foundRoute, value);
		}
		return foundRoute;
	}

	public boolean isRightRoute(String route) {
		return route.matches("[^" + startPoint + "]*" + startPoint + "[^"
				+ endPoint + "]*" + endPoint + ".*");
	}

	

	@Override
	public void startDocument() throws SAXException {
		transportList = new ArrayList<Transport>();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		current = localName;
		if ("Bus".equals(current) || "Trolleybus".equals(current)) {
			transport = new Transport();
			isRightTransport = true;
		}
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public List<Transport> getTransportList() {
		return transportList;
	}

	public void setTransportList(List<Transport> trList) {
		this.transportList = trList;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
