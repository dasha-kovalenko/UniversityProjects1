package by.kovalenko.football.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import by.kovalenko.football.dao.IMatchDatabase;
import by.kovalenko.football.dao.MatchDatabase;
import by.kovalenko.football.domain.Match;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class MatchController extends ActionSupport implements
		ModelDriven<Match> {

	private static final long serialVersionUID = 1L;

	private Match match = new Match();
	private List<Match> matchList = new ArrayList<Match>();
	private List<String> teamList = new ArrayList<String>();
	private IMatchDatabase matchDatabase;

	private int id;

	private String count1;
	private String count2;
	private String team;
	private String formattedDate;

	{
		try {
			matchDatabase = new MatchDatabase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String create() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		return SUCCESS;
	}

	public String save() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		String count1 = request.getParameter("count1");
		String count2 = request.getParameter("count2");
		String count = String.format("%s:%s", count1, count2);
		match.setCount(count);
		match.setMatchdate(Date.valueOf(request.getParameter("formattedDate")));
		matchDatabase.insertMatch(match);
		return SUCCESS;
	}

	public String update() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		String count1 = request.getParameter("count1");
		String count2 = request.getParameter("count2");
		String count = String.format("%s:%s", count1, count2);
		match.setCount(count);
		match.setMatchdate(Date.valueOf(request.getParameter("formattedDate")));
		matchDatabase.updateMatch(match);
		return SUCCESS;
	}

	public String list() {
		if (!isLoggedIn())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		if (!request.getParameterMap().isEmpty()
				&& !(request.getParameterMap().size() == 1)) {
			Date startDate = Date.valueOf(request.getParameter("startDate"));
			Date finishDate = Date.valueOf(request.getParameter("finishDate"));
			matchList = matchDatabase
					.selectMatches(team, startDate, finishDate);
		} else {
			matchList = matchDatabase.showDataBase();
		}
		teamList = matchDatabase.getTeams();
		return SUCCESS;
	}

	public String delete() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		matchDatabase.deleteMatch(Integer.parseInt(request.getParameter("id")));
		return SUCCESS;
	}

	public String edit() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		match = matchDatabase.getMatch(Integer.parseInt(request
				.getParameter("id")));
		count1 = match.getCount().substring(0, match.getCount().indexOf(':'));
		count2 = match.getCount().substring(match.getCount().indexOf(':') + 1,
				match.getCount().length());
		if (match.getMatchdate() != null)
			formattedDate = match.getMatchdate().toString();
		else
			formattedDate = "";
		return SUCCESS;
	}

	public boolean isLoggedIn() {
		Map session = ActionContext.getContext().getSession();
		if (session.containsKey("userId"))
			return true;
		else
			return false;
	}

	public boolean isLoggedInAsAdmin() {
		Map session = ActionContext.getContext().getSession();
		if (session.containsKey("userId") && session.containsKey("admin")
				&& session.get("admin").equals(true))
			return true;
		else
			return false;
	}

	public void validateList() {
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		teamList = matchDatabase.getTeams();
		matchList = matchDatabase.showDataBase();
		if (request.getParameterMap().containsKey("team")
				&& !request.getParameter("team").matches(
						"[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("team", getText("validation.not_correct.team"));
		if (request.getParameterMap().containsKey("startDate")
				&& !request.getParameter("startDate").matches(
						"[0-9]{4}-[0-9]{2}-[0-9]{2}"))
			addFieldError("", getText("validation.not_correct.startDate"));
		if (request.getParameterMap().containsKey("finishDate")
				&& !request.getParameter("finishDate").matches(
						"[0-9]{4}-[0-9]{2}-[0-9]{2}"))
			addFieldError("", getText("validation.not_correct.finishDate"));
		if (request.getParameterMap().containsKey("finishDate")
				&& request.getParameter("startDate").matches(
						"[0-9]{4}-[0-9]{2}-[0-9]{2}")
				&& request.getParameterMap().containsKey("startDate")
				&& request.getParameter("finishDate").matches(
						"[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
			try {
				Date sDate = Date.valueOf(request.getParameter("startDate"));
				Date fDate = Date.valueOf(request.getParameter("finishDate"));
				if (sDate.after(fDate)) {
					addFieldError(
							"startDate and finishDate",
							getText("validation.not_correct.startDateaAfterFinishDate"));
				}
			} catch (IllegalArgumentException e) {
				addFieldError("", getText("validation.not_correct.startDate"));
				addFieldError("", getText("validation.not_correct.finishDate"));
			}
		}
	}

	public void validateSave() {
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		if (request.getParameterMap().containsKey("team1")
				&& !request.getParameter("team1").matches(
						"[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("team1", getText("validation.not_correct.team1"));
		if (request.getParameterMap().containsKey("team2")
				&& !request.getParameter("team2").matches(
						"[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("team2", getText("validation.not_correct.team2"));
		if (request.getParameter("count1") != null
				&& !request.getParameter("count1").matches("[0-9]")) {
			addFieldError("count1", getText("validation.not_correct.count1"));
		}
		if (request.getParameter("count2") != null
				&& !request.getParameter("count2").matches("[0-9]")) {
			addFieldError("count2", getText("validation.not_correct.count2"));
		}
		if (request.getParameter("formattedDate") != null
				&& !request.getParameter("formattedDate").matches(
						"[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
			addFieldError("formattedDate",
					getText("validation.not_correct.formattedDate"));
		}
	}

	public void validateUpdate() {
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		if (request.getParameterMap().containsKey("team1")
				&& !request.getParameter("team1").matches(
						"[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("team1", getText("validation.not_correct.team1"));
		if (request.getParameterMap().containsKey("team2")
				&& !request.getParameter("team2").matches(
						"[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("team2", getText("validation.not_correct.team2"));
		if (request.getParameter("count1") != null
				&& !request.getParameter("count1").matches("[0-9]")) {
			addFieldError("count1", getText("validation.not_correct.count1"));
		}
		if (request.getParameter("count2") != null
				&& !request.getParameter("count2").matches("[0-9]")) {
			addFieldError("count2", getText("validation.not_correct.count2"));
		}
		if (request.getParameter("formattedDate") != null
				&& !request.getParameter("formattedDate").matches(
						"[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
			addFieldError("formattedDate",
					getText("validation.not_correct.formattedDate"));
		}
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	public String getCount1() {
		return count1;
	}

	public void setCount1(String count1) {
		this.count1 = count1;
	}

	public String getCount2() {
		return count2;
	}

	public void setCount2(String count2) {
		this.count2 = count2;
	}

	@Override
	public Match getModel() {
		return match;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public IMatchDatabase getMatchDatabase() {
		return matchDatabase;
	}

	public void setMatchDatabase(IMatchDatabase matchDatabase) {
		this.matchDatabase = matchDatabase;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public List<Match> getMatchList() {
		return matchList;
	}

	public void setMatchList(List<Match> matchList) {
		this.matchList = matchList;
	}

	public List<String> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<String> teamList) {
		this.teamList = teamList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
