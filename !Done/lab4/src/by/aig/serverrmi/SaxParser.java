package by.aig.serverrmi;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.aig.common.Match;

public class SaxParser extends DefaultHandler {
	
	private Match match;
	private List<Match>resList;
	private String current;
	
//parametri poiska
	private String team;
	private Date startDate;
	private Date endDate;
	
	private boolean accepted;
	//private boolean teamAccepted;
	//private boolean startAccepted;
	//private boolean endAccepted;
	
	public SaxParser(String team, Date start, Date end) {
		super();
		this.team = team;
		this.startDate = start;
		this.endDate = end;
	}

	public List<Match> getResList() {
		return resList;
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		resList = new ArrayList<Match>();
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName,
			Attributes atts) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(namespaceURI, localName, qName, atts);
		current = localName;
		if("match".equals(current)){
			match = new Match();
			accepted = true;
			//teamAccepted = team==null ? true : false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(!accepted) return;
		String element = new String(ch, start, length);
		if (current.equals("id")) 
			match.setId(new Integer(element));
		else if(current.equals("team1")){
			if(team.equals(element))
				match.setTeam1(element);
			else accepted =  false;
			System.out.print(element+" ");

		}
		else if(current.equals("team2")){
			if(team.equals(element))
				match.setTeam2(element);
			else accepted =  false;
			System.out.print(element+" ");

		}
		else if(current.equals("matchdate")){
			Date date = Date.valueOf(element);
			if(date.after(startDate) && date.before(endDate))
				match.setMatchdate(date);
			else accepted =  false;
			System.out.print(date+" ");

		}
		else if(current.equals("count"))
			match.setCount(element);
		
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		current = "";
		if ("match".equals(localName) && accepted)
			resList.add(match);

	}

	


	
	
	
}
