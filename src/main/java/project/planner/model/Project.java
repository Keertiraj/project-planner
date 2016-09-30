package project.planner.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Project Entity class to store the project details.
 */
@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	private int startDateWeek;

	private int endDateWeek;

	@Enumerated(EnumType.STRING)
	private Status status;

	private int durationInWeeks;

	private String comments;

	@OneToMany(mappedBy = "projectMap", cascade={CascadeType.REFRESH, CascadeType.MERGE},fetch=FetchType.EAGER)
	private Set<ProjectSkill> projectSkills;

	@OneToMany(mappedBy = "project", cascade={CascadeType.REFRESH, CascadeType.MERGE},fetch=FetchType.EAGER)
	private Set<EmployeeProject> employeeProjects;

	public Project() {
		projectSkills = new HashSet<>();
	}

	public Project(String name) {
		this.name = name;
		projectSkills = new HashSet<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartDateWeek() {
		return startDateWeek;
	}

	public void setStartDateWeek(int startDateWeek) {
		this.startDateWeek = startDateWeek;
	}

	public int getEndDateWeek() {
		return endDateWeek;
	}

	public void setEndDateWeek(int endDateWeek) {
		this.endDateWeek = endDateWeek;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getDurationInWeeks() {
		return durationInWeeks;
	}

	public void setDurationInWeeks(int durationInWeeks) {
		this.durationInWeeks = durationInWeeks;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<ProjectSkill> getProjectSkills() {
		return projectSkills;
	}

	public void setProjectSkills(Set<ProjectSkill> projectSkills) {
		this.projectSkills = projectSkills;
	}

	public Set<EmployeeProject> getEmployeeProjects() {
		return employeeProjects;
	}

	public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
		this.employeeProjects = employeeProjects;
	}
	
	@Override
	public String toString() {
		String result = String.format("Project  Skill[id=%d, name='%s']%n", id, name);
		if (projectSkills != null) {
			for (ProjectSkill skill : projectSkills) {
				result += String.format("Skill[id=%d, name='%s']%n", skill.getId(), skill.getSkillMap());
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	        return false;

	    if(!(o instanceof Project))
	        return false;

	    if(getName() == null)
	        return false;

	    Project project = (Project) o;
	    if(!(getName().equals(project.getName())))
	        return false;
	    
	    if(!(getComments().equals(project.getComments())))
	        return false;


	   return true;
	}

	
}
