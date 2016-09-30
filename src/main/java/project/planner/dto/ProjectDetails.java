package project.planner.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object to store the Project details.
 */
public class ProjectDetails {

	private String name;
	private Map<String, String> projectSkills = new HashMap<String, String>();
	private int duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getProjectSkills() {
		return projectSkills;
	}

	public void setProjectSkills(Map<String, String> projectSkills) {
		this.projectSkills = projectSkills;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
