package project.planner.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object to response of Input File.
 */
public class Response {

	private String response = "";
	private String error = "";

	private Map<String, String> employeesSkills = new HashMap<String, String>();
	private List<ProjectDetails> projects = new ArrayList<ProjectDetails>();

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, String> getEmployeesSkills() {
		return employeesSkills;
	}

	public void setEmployeesSkills(Map<String, String> employeesSkills) {
		this.employeesSkills = employeesSkills;
	}

	public List<ProjectDetails> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDetails> projects) {
		this.projects = projects;
	}

}