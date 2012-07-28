package by.kovalenko.football.controller;

import com.opensymphony.xwork2.ActionSupport;

public class LocaleAction extends ActionSupport{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	
	public String execute() {
		return SUCCESS;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}