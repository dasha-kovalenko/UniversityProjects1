package by.kdv.XMLparsers;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.kdv.factory.TransportFactory;
import by.kdv.transport.Bus;
import by.kdv.transport.Transport;
import by.kdv.transport.Trolleybus;

public class TransportDOMHandler {

	private File xmlFile;

	public TransportDOMHandler(String xmlFile) {
		this.xmlFile = new File(xmlFile);
	}

	public TransportDOMHandler(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	public List<Transport> getTransportListByRoute(String startPoint,
			String endPoint) {
		List<Transport> transportList = new LinkedList<Transport>();
		Document document;
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(xmlFile);
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Transport transport = new Transport();
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					NodeList fields = node.getChildNodes();
					boolean isRightTransport = true;
					for (int j = 0; j < fields.getLength(); j++) {
						Node fieldNode = fields.item(j);
						if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
							String fieldName = getLocalName(fieldNode
									.getNodeName());
							String fieldContent = fieldNode.getTextContent();
							if (fieldName.equals("number")) {
								transport.setNumber(new Integer(fieldContent));
							} else if (fieldName.equals("start")) {
								try {
									transport.setStart(DateFormat
											.getTimeInstance().parse(
													fieldContent));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							} else if (fieldName.equals("finish")) {
								try {
									transport.setFinish(DateFormat
											.getTimeInstance().parse(
													fieldContent));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							} else if (fieldName.equals("route")) {
								String route = new String(fieldContent);
								transport.setRoute(route);
								if (!isRightRoute(startPoint, endPoint, route)) {
									isRightTransport = false;
									break;
								}
							} else if (fieldName.equals("price")) {
								transport.setValue(new Integer(fieldContent));

							} else if (fieldName.equals("interval")) {
								transport
										.setInterval(new Integer(fieldContent));

							} else if (fieldName.equals("capacity")) {
								transport
										.setCapacity(new Integer(fieldContent));

							} else if (fieldName.equals("amountOfFuel")) {
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
								((Bus) t1).setAmountOfFuel(new Integer(
										fieldContent));

								transport = t1;
								// System.out.println(t1.toString());

							} else if (fieldName.equals("energy")) {
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
								((Trolleybus) t2).setEnergy(new Integer(
										fieldContent));
								// System.out.println(t2.toString());

								transport = t2;
							}
						}
					}
					if (isRightTransport)
						transportList.add(transport);
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return transportList;
	}

	private String getLocalName(String name) {
		return name.substring(name.lastIndexOf(':') + 1, name.length());
	}

	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	public boolean isRightRoute(String startPoint, String endPoint, String route) {
		return route.matches("[^" + startPoint + "]*" + startPoint + "[^"
				+ endPoint + "]*" + endPoint + ".*");
	}

	/*
	 * private void visit(Node node, int level) { NodeList nl =
	 * node.getChildNodes(); String parent = new String(""); int cnt =
	 * nl.getLength(); for (int i = 0; i < cnt; i++) { if
	 * (nl.item(i).getNodeType() == Node.TEXT_NODE) { parent =
	 * nl.item(i).getParentNode().getNodeName(); txt =
	 * nl.item(i).getNodeValue(); if (parent.equals("name")) { fl.setName(txt);
	 * System.out.println(nl.item(i).getNodeName() + " = " +
	 * nl.item(i).getNodeValue()); } if (parent.equals("origin")) {
	 * fl.setOrigin(txt); System.out.println(nl.item(i).getNodeName() + " = " +
	 * nl.item(i).getNodeValue()); } if (parent.equals("temperature")) {
	 * fl.setTemperature(Integer.valueOf(txt));
	 * System.out.println(nl.item(i).getNodeName() + " = " +
	 * nl.item(i).getNodeValue()); } if (parent.equals("picture")) {
	 * fl.setPicture(txt); System.out.println(nl.item(i).getNodeName() + " = " +
	 * nl.item(i).getNodeValue()); } } else { if
	 * (nl.item(i).getNodeName().equals("flower")) { fl = new Flower();
	 * fls.add(fl); System.out
	 * .println("****************************************"); } }
	 * visit(nl.item(i), level + 1); }
	 * 
	 * }
	 */

}
