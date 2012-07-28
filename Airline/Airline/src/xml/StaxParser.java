package xml;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import planes.FreightPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

/**
 *
 * @author Yuliya
 */
public class StaxParser {

    public ArrayList parseXML(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);

        Plane plane = null;
        ArrayList planes = new ArrayList();

        String s;
        while (reader.hasNext()) {
            int type = reader.next();

            switch (type) {
                
                case XMLStreamConstants.START_ELEMENT:
                    s = reader.getLocalName();
                    if (s.equals("passenger")) {
                        plane = new PassengerPlane("", 0, 0, 0, 0);
                    }
                    if (s.equals("freight")) {
                        plane = new FreightPlane("", 0, 0, 0, 0);
                    }
                    if (s.equals("military")) {
                        plane = new MilitaryPlane("", 0, 0, 0, 0);
                    }
                    if (s.equals("name")) {
                        plane.setName(reader.getElementText());
                    }
                    if (s.equals("capacity")) {
                        plane.setCapacity(Integer.parseInt(reader.getElementText()));
                    }
                    if (s.equals("speed")) {
                        plane.setSpeed(Integer.parseInt(reader.getElementText()));
                    }
                    if (s.equals("tonnage")) {
                        plane.setTonnage(Integer.parseInt(reader.getElementText()));
                    }
                    if (s.equals("distance")) {
                        plane.setDistance(Integer.parseInt(reader.getElementText()));
                    }
                    if (s.equals("price")) {
                        ((PassengerPlane) plane).setPrice(Integer.parseInt(reader.getElementText()));
                    }
                    if (s.equals("calibreWeight")) {
                        ((MilitaryPlane) plane).setCalibreWeight(Integer.parseInt(reader.getElementText()));
                    }
                    if (s.equals("freightType")) {
                        ((FreightPlane) plane).setFreightType(reader.getElementText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    s = reader.getLocalName();
                    if (s.equals("passenger") || s.equals("freight") || s.equals("military")) {
                        planes.add(plane);
                    }
                    if (s.equals("planes")) {
                        return planes;
                    }
                    break;
            }
        }
        return planes;
    }
}
