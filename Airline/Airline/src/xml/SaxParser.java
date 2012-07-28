package xml;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import planes.FreightPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

interface ConstElements {

    int NAME = 1, CAPACITY = 2, SPEED = 3, TONNAGE = 4, DISTANCE = 5, PRICE = 6, FREIGHT_TYPE = 7, CALIBRE_WEIGHT = 8;
}

public class SaxParser extends DefaultHandler implements ConstElements {

    ArrayList planes = new ArrayList();
    Plane plane;
    int current = -1;

    public ArrayList getPlanes() {
        return planes;
    }

    @Override
    public void startElement(String uri,
            String localName, String qName,
            Attributes attrs) {
        if (qName.equals("passenger")) {
            plane = new PassengerPlane("", 0, 0, 0, 0);

        }
        if (qName.equals("military")) {
            plane = new MilitaryPlane("", 0, 0, 0, 0);
        }
        if (qName.equals("freight")) {
            plane = new FreightPlane("", 0, 0, 0, 0);
        }
        if (qName.equals("name")) {
            current = NAME;
        }
        if (qName.equals("capacity")) {
            current = CAPACITY;
        }
        if (qName.equals("speed")) {
            current = SPEED;
        }
        if (qName.equals("tonnage")) {
            current = TONNAGE;
        }
        if (qName.equals("distance")) {
            current = DISTANCE;
        }
        if (qName.equals("calibreWeight")) {
            current = CALIBRE_WEIGHT;
        }
        if (qName.equals("freightType")) {
            current = FREIGHT_TYPE;
        }
        if (qName.equals("price")) {
            current = PRICE;
        }
    }

    @Override
    public void endElement(String uri,
            String localName, String qName) {
        if (qName.equals("passenger")) {
            planes.add(plane);
        }
        if (qName.equals("military")) {
            planes.add(plane);
        }
        if (qName.equals("freight")) {
            planes.add(plane);
        }
        if (qName.equals("name")) {
            current = -1;
        }
        if (qName.equals("capacity")) {
            current = -1;
        }
        if (qName.equals("speed")) {
            current = -1;
        }
        if (qName.equals("tonnage")) {
            current = -1;
        }
        if (qName.equals("distance")) {
            current = -1;
        }
        if (qName.equals("calibreWeight")) {
            current = -1;
        }
        if (qName.equals("freightType")) {
            current = -1;
        }
        if (qName.equals("price")) {
            current = -1;
        }
    }

    @Override
    public void characters(char[] ch,
            int start, int length) {
        String s = new String(ch, start, length);
        try {
            switch (current) {
                case NAME:
                    plane.setName(s);
                    break;
                case CAPACITY:
                    plane.setCapacity(Integer.parseInt(s));
                    break;
                case SPEED:
                    plane.setSpeed(Integer.parseInt(s));
                    break;
                case TONNAGE:
                    plane.setTonnage(Integer.parseInt(s));
                    break;
                case DISTANCE:
                    plane.setDistance(Integer.parseInt(s));
                    break;
                case CALIBRE_WEIGHT:
                    ((MilitaryPlane) plane).setCalibreWeight(Integer.parseInt(s));
                    break;
                case FREIGHT_TYPE:
                    ((FreightPlane) plane).setFreightType(s);
                    break;
                case PRICE:
                    ((PassengerPlane) plane).setPrice(Integer.parseInt(s));
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
