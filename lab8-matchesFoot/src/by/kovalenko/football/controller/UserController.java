package by.kovalenko.football.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import by.kovalenko.football.dao.IUserDatabase;
import by.kovalenko.football.dao.UserDatabase;
import by.kovalenko.football.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserController extends ActionSupport implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName = "";
	private String loginPassword = "";

	private User user = new User();
	private List<User> userList = new ArrayList<User>();

	private IUserDatabase userDatabase;

	{
		try {
			userDatabase = new UserDatabase();
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
		return SUCCESS;
	}

	public String save() {
		userDatabase.insertUser(user);
		return SUCCESS;
	}

	public String update() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		userDatabase.updateUser(user);
		return SUCCESS;
	}

	public String list() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		userList = userDatabase.listUsers();
		return SUCCESS;
	}

	public String delete() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		userDatabase.deleteUser(Integer.parseInt(request.getParameter("id")));
		return SUCCESS;
	}

	public String edit() {
		if (!isLoggedInAsAdmin())
			return ERROR;
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		user = userDatabase
				.getUser(Integer.parseInt(request.getParameter("id")));
		return SUCCESS;
	}

	//autorization
	public String login() {
		if (isLoggedIn())
			return SUCCESS;
		User user = null;
		try {
			user = userDatabase.getUser(loginName, loginPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			Map session = ActionContext.getContext().getSession();
			session.put("userId", user.getId());
			session.put("admin", user.isAdmin());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	//авторизация
	public String logoff() {
		Map session = ActionContext.getContext().getSession();
		session.remove("userId");
		session.remove("admin");
		return SUCCESS;
	}

	public String loginPage() {
		if (isLoggedIn())
			return "listMatch";
		Map session = ActionContext.getContext().getSession();
		session.remove("userId");
		session.remove("admin");
		return SUCCESS;
	}

	public void validateSave() {
		try {
			if (userDatabase.getUser(user.getName()) != null)
				addFieldError("candidate.name",
						getText("validation.not_correct.name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!user.getPassword().equals(user.getPasswordConfirmation())) {
			addFieldError("candidate.password",
					getText("validation.not_correct.password"));
		}
		if (!user.getName().matches("[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("candidate.name",
					getText("validation.not_correct.name"));
		if (user.getPassword().length() <= 4) {
			addFieldError("candidate.password",
					getText("validation.not_correct.password"));
		}
	}

	public void validateUpdate() {
		try {
			if (userDatabase.getUser(user.getName()).equals(user))
				addFieldError("candidate.name",
						getText("validation.not_correct.name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!user.getPassword().equals(user.getPasswordConfirmation())) {
			addFieldError("candidate.password",
					getText("validation.not_correct.password"));
		}
		if (!user.getName().matches("[a-zA-Zа-яА-Я ]{3,15}"))
			addFieldError("candidate.name",
					getText("validation.not_correct.name"));
		if (user.getPassword().length() <= 4) {
			addFieldError("candidate.password",
					getText("validation.not_correct.password"));
		}
	}

	//autorization
	public void validateLogin() {
		try {
			user = userDatabase.getUser(loginName, loginPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			addFieldError(loginName, getText("authentication.failed"));
		}
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public IUserDatabase getUserDatabase() {
		return userDatabase;
	}

	public void setUserDatabase(IUserDatabase userDatabase) {
		this.userDatabase = userDatabase;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Override
	public User getModel() {
		return user;
	}

}
