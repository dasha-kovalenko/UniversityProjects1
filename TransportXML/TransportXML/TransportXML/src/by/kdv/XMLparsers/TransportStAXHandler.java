package by.kdv.XMLparsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.kdv.factory.TransportFactory;
import by.kdv.transport.Bus;
import by.kdv.transport.Transport;
import by.kdv.transport.Trolleybus;

public class TransportStAXHandler {
	// create an XMLStreamReader

	private File xmlFile;

	public TransportStAXHandler(String xmlFile) {
		this.xmlFile = new File(xmlFile);
	}

	public TransportStAXHandler(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	public List<Transport> getTransportListByRoute(String startPoint,
			String endPoint) {

		String current = "";
		Transport transport = null;
		List<Transport> transportList = null;

		XMLStreamReader reader = null;
		boolean isRightTransport = false;
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			reader = factory.createXMLStreamReader(new FileReader(xmlFile));

			int event = reader.getEventType();
			while (true) {
				switch (event) {
				case XMLStreamConstants.START_DOCUMENT:
					// out.println("Start Document.");
					transportList = new ArrayList<Transport>();
					break;
				case XMLStreamConstants.START_ELEMENT:
					// out.println("Start Element: " + r.getName());
					current = reader.getLocalName();
					if ("Bus".equals(current) || "Trolleybus".equals(current)) {
						transport = new Transport();
						isRightTransport = true;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (reader.isWhiteSpace() || !isRightTransport)
						break;
					else {
						String element = reader.getText();
						if ("number".equals(current)) {
							transport.setNumber(new Integer(element));

						} else if (current.equals("start")) {
							try {
								transport.setStart(DateFormat.getTimeInstance()
										.parse(element));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						} else if (current.equals("finish")) {
							try {
								transport.setFinish(DateFormat
										.getTimeInstance().parse(element));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						} else if (current.equals("route")) {
							String routeElement = new String(element);
							transport.setRoute(routeElement);
							if (!isRightRoute(startPoint, endPoint,
									routeElement))
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
							// System.out.println(cal.get(Calendar.HOUR));
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(transport.getFinish());

							Transport t1 = TransportFactory
									.getClassFromFactory("bus",
											transport.getCapacity(),
											transport.getNumber(),
											cal.get(Calendar.HOUR),
											cal.get(Calendar.MINUTE),
											cal1.get(Calendar.HOUR),
											cal1.get(Calendar.MINUTE),
											transport.getRoute(),
											transport.getValue(),
											transport.getInterval());
							((Bus) t1).setAmountOfFuel(new Integer(element));

							transport = t1;
							// System.out.println(t1.toString());

						} else if (current.equals("energy")) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(transport.getStart());
							// System.out.println(cal.get(Calendar.HOUR));
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(transport.getFinish());
							Transport t2 = TransportFactory
									.getClassFromFactory("trolleybus",
											transport.getCapacity(),
											transport.getNumber(),
											cal.get(Calendar.HOUR),
											cal.get(Calendar.MINUTE),
											cal1.get(Calendar.HOUR),
											cal1.get(Calendar.MINUTE),
											transport.getRoute(),
											transport.getValue(),
											transport.getInterval());
							((Trolleybus) t2).setEnergy(new Integer(element));
							// System.out.println(t2.toString());

							transport = t2;
						}
					}

					// out.println("Text: " + r.getText());
					break;
				case XMLStreamConstants.END_ELEMENT:
					current = reader.getLocalName();
					if (("Bus".equals(current) || "Trolleybus".equals(current))
							&& isRightTransport) {
						transportList.add(transport);
					}
					current = "";
					break;
				case XMLStreamConstants.END_DOCUMENT:
					// out.println("End Document.");
					break;
				}

				if (!reader.hasNext())
					break;

				event = reader.next();
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!(reader == null))
					reader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
		return transportList;
	}

	public boolean isRightRoute(String startPoint, String endPoint, String route) {
		return route.matches("[^" + startPoint + "]*" + startPoint + "[^"
				+ endPoint + "]*" + endPoint + ".*");
	}

}
