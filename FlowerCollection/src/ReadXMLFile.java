import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.table.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class ReadXMLFile {
	private Document doc = null;
	private String txt = new String("");
	private Flower fl = new Flower(); 
	private ArrayList<Flower> fls = null; 
 
	public ReadXMLFile(String fname){
		try {
		   doc = parserXML(new File(fname));
		   fls = new ArrayList<Flower>();
		   visit(doc, 0);
		}
		catch(Exception ex){
		   ex.printStackTrace();
		}

	}
	public void visit(Node node, int level) {
	  NodeList nl = node.getChildNodes();  
	  String parent=new String("");
	  int cnt=nl.getLength();
	  for(int i=0; i<cnt; i++){   
		   if (nl.item(i).getNodeType()==Node.TEXT_NODE){ 
			   parent=nl.item(i).getParentNode().getNodeName();
			   txt=nl.item(i).getNodeValue();
			   if (parent.equals("name")){ 
				   fl.setName(txt); 
				   System.out.println(nl.item(i).getNodeName() + " = " + nl.item(i).getNodeValue());
			   }
			    if (parent.equals("origin")){ 
			    	fl.setOrigin(txt);
			    	 System.out.println(nl.item(i).getNodeName() + " = " + nl.item(i).getNodeValue());
			    }
			    if (parent.equals("temperature")){   
			    	fl.setTemperature(Integer.valueOf(txt));
			    	 System.out.println(nl.item(i).getNodeName() + " = " + nl.item(i).getNodeValue());
			    }
			    if(parent.equals("picture")){
			    	fl.setPicture(txt);
			    	System.out.println(nl.item(i).getNodeName() + " = " + nl.item(i).getNodeValue());
			    }
		   } 
		   else {
			   if (nl.item(i).getNodeName().equals("flower")){ 
				   fl=new Flower();
				   fls.add(fl);
				   System.out.println("****************************************");
		  }
	   }
	   visit(nl.item(i), level+1);
	  }
	  
	}
	public Document parserXML(File file) throws SAXException, IOException, ParserConfigurationException {
	  return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
	}
	public ArrayList<Flower> getFls() {
	  return fls;
	}
	public void setFls(ArrayList<Flower> fls) {
	  this.fls = fls;
	}
}


