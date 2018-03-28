package com.sanbhu.deployment.script.bo;

import java.util.ArrayList;
import java.util.List;

public class SVNLogDetail {
	private String author;
	private Long revisionNumber;
	private String message;
	private String date;
	private List<String> changeListPaths = new ArrayList<String>();
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Long getRevisionNumber() {
		return revisionNumber;
	}
	
	public void setRevisionNumber(Long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public List<String> getChangeListPaths() {
		return changeListPaths;
	}

	public void setChangeListPaths(List<String> changeListPaths) {
		this.changeListPaths = changeListPaths;
	}
}
