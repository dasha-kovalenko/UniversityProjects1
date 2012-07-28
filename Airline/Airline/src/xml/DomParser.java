package xml;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import planes.FreightPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

/**
 *
 * @author Yuliya
 */
public class DomParser {

    public static String getValue(Element e, String name) {
        NodeList nList = e.getElementsByTagName(name);
        Element elem = (Element) nList.item(0);
        Text t = (Text) elem.getFirstChild();

        return t.getNodeValue();
    }

    public void generatePlanes(NodeList root, String type, ArrayList planes) {
        Element noteElem;
        Plane e = null;
        for (int i = 0; i < root.getLength(); i++) {
            noteElem = (Element) root.item(i);
            if (type.equals("passenger")) {
                e = new PassengerPlane("", 0, 0, 0, 0);
            }
            if (type.equals("military")) {
                e = new MilitaryPlane("", 0, 0, 0, 0);
            }
            if (type.equals("freight")) {
                e = new FreightPlane("", 0, 0, 0, 0);
            }
            e.setName(getValue(noteElem, "name"));
            e.setCapacity(Integer.parseInt(getValue(noteElem, "capacity")));
            e.setDistance(Integer.parseInt(getValue(noteElem, "distance")));
            e.setSpeed(Integer.parseInt(getValue(noteElem, "speed")));
            e.setTonnage(Integer.parseInt(getValue(noteElem, "tonnage")));
            if (type.equals("passenger")) {
                ((PassengerPlane) e).setPrice(Integer.parseInt(getValue(noteElem, "price")));
            }
            if (type.equals("military")) {
                ((MilitaryPlane) e).setCalibreWeight(Integer.parseInt(getValue(noteElem, "calibreWeight")));
            }
            if (type.equals("freight")) {
                ((FreightPlane) e).setFreightType(getValue(noteElem, "freightType"));
            }
            planes.add(e);
        }
    }

    public ArrayList parseXML(String name) {
        Document doc;
        DOMParser parser = new DOMParser();
        ArrayList planes = new ArrayList();

        try {
            parser.parse(name);
            doc = parser.getDocument();
            Element root = doc.getDocumentElement();
            NodeList noteList;

            noteList = root.getElementsByTagName("passenger");
            generatePlanes(noteList, "passenger", planes);

            noteList = root.getElementsByTagName("freight");
            generatePlanes(noteList, "freight", planes);

            noteList = root.getElementsByTagName("military");
            generatePlanes(noteList, "military", planes);

        } catch (Exception e) {
            System.out.println(e);
        }
        return planes;
    }
}
