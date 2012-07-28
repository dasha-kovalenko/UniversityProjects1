package by.kdv.serverrmi;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.kdv.common.Match;

public class SaxParser extends DefaultHandler {
	
	private Match match;
	private List<Match>resList;
	private String current;
	
	private String team;
	private Date startDate;
	private Date endDate;
	
	private boolean team1Accepted;
	private boolean team2Accepted;
	private boolean dateAccepted;

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
		super.startElement(namespaceURI, localName, qName, atts);
		current = localName;
		if("match".equals(current)){
			match = new Match();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String element = new String(ch, start, length);
		if (current.equals("id")) 
			match.setId(new Integer(element));
		else if(current.equals("team1")){
			if(!team.equals("any")){
				if(team.equals(element)){
					match.setTeam1(element);
					team1Accepted = true;
					team2Accepted = false;
				}
				else{
					match.setTeam1(element);
				}
			}else {
				match.setTeam1(element);
				team1Accepted = true;
			}

		}
		else if(current.equals("team2")){
			if(!team1Accepted){
				if(team.equals(element)){
					match.setTeam2(element);
					team2Accepted = true;
					
				}
			}
			else {
				match.setTeam2(element);
				team2Accepted = true;
			}
		}
		else if(current.equals("matchdate")){
			Date date = Date.valueOf(element);
			if(team1Accepted || team2Accepted)
				if((date.compareTo(startDate)>=0) && (date.compareTo(endDate)<=0)){
					match.setMatchdate(date);
					dateAccepted = true;
					
				}
		}
		else if(current.equals("count"))
			if(team1Accepted || team2Accepted)
				match.setCount(element);
		
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		current = "";
		if (("match".equals(localName) && team1Accepted  && dateAccepted )){
			resList.add(match);
			team1Accepted = false;
			team2Accepted = false;
			dateAccepted = false;
		}else if((("match".equals(localName) && team2Accepted && dateAccepted))){
			resList.add(match);
			team1Accepted = false;
			team2Accepted = false;
			dateAccepted = false;
		}
	}
}
