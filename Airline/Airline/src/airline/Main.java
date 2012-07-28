package airline;

import comparators.DistanceComparator;
import comparators.SpeedComparator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;
import planes.FreightPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;
import xml.DomParser;
import xml.SaxParser;
import xml.StaxParser;

public class Main {

    public static final String XML_FILE = "D:/epam/Airline/src/xml/airlinep.xml";

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException, SAXException, IOException, ParserConfigurationException {

        Airline airline = new Airline();

        Plane plane1 = Airline.getPlane("military", "Mig-AT", 4, 850, 1000, 2000);
        ((MilitaryPlane) plane1).setCalibreWeight(500);
        Plane plane2 = Airline.getPlane("passenger", "Boeing-707", 179, 1500, 1000, 1500);
        ((PassengerPlane) plane2).setPrice(1000);
        Plane plane3 = Airline.getPlane("passenger", "TY", 250, 700, 1000, 5700);
        ((PassengerPlane) plane3).setPrice(1000);
        Plane plane4 = Airline.getPlane("passenger", "IL", 100, 900, 1000, 4300);
        ((PassengerPlane) plane4).setPrice(1000);
        Plane plane5 = Airline.getPlane("passenger", "Boeing-747", 200, 750, 1000, 5000);
        ((PassengerPlane) plane5).setPrice(1000);
        Plane plane6 = Airline.getPlane("freight", "IL-114-100T", 2, 500, 1000, 1000);
        ((FreightPlane) plane6).setFreightType("продукты питания");

        airline.add(plane1);
        airline.add(plane2);
        airline.add(plane3);
        airline.add(plane4);
        airline.add(plane5);
        airline.add(plane6);

        System.out.println("Общая вместимость: " + airline.countGeneralCapacity());
        System.out.println("Общая грузоподъёмность: " + airline.countGeneralTonnage());

        System.out.println("\n Список самолётов: ");
        for (int i = 0; i < airline.size(); i++) {
            System.out.println(airline.get(i).toString());
        }

        System.out.println("\n Сортировка по скорости: ");
        SpeedComparator speedComparator = new SpeedComparator();
        Collections.sort(airline, speedComparator);
        for (int i = 0; i < airline.size(); i++) {
            System.out.println(airline.get(i));
        }

        System.out.println("\n Сортировка по дальности полёта: ");
        DistanceComparator distanceComparator = new DistanceComparator();
        Collections.sort(airline, distanceComparator);
        for (int i = 0; i < airline.size(); i++) {
            System.out.println(airline.get(i));
        }

        ArrayList<Plane> planes = airline.findCapacities(1, 150);
        System.out.println("\n Самолёты с вместимостью из заданного диапазона: ");
        for (int i = 0; i < planes.size(); i++) {
            System.out.println(planes.get(i));
        }

        SaxParser parserS = new SaxParser();
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(XML_FILE, parserS);
        System.out.println("SAX: " + parserS.getPlanes());

        DomParser domParser = new DomParser();
        System.out.println("DOM: " + domParser.parseXML(XML_FILE));

        StaxParser staxParser = new StaxParser();
        InputStream input = new FileInputStream(XML_FILE);
        System.out.println("StAX: " + staxParser.parseXML(input));
    }
}