package project.planner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProjectSkill Entity class to store the Project's Skill details.
 */
@Entity
@Table(name = "project_skill")
public class ProjectSkill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project projectMap;

	@ManyToOne
	@JoinColumn(name = "skill_id")
	private Skill skillMap;

	private String quantity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Project getProjectMap() {
		return projectMap;
	}

	public void setProjectMap(Project projectMap) {
		this.projectMap = projectMap;
	}

	public Skill getSkillMap() {
		return skillMap;
	}

	public void setSkillMap(Skill skillMap) {
		this.skillMap = skillMap;
	}
	
	@Override
	public String toString() {
		String result = String.format("Project  Skill[id=%d, Employee='%s']%n", id, projectMap.getName());
				result += String.format("Skill[id=%d, name='%s']%n", skillMap.getId(), skillMap.getName());
			
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	        return false;

	    if(!(o instanceof EmployeeSkill))
	        return false;

	    if(getSkillMap() == null)
	        return false;

	    ProjectSkill projectSkill = (ProjectSkill) o;
	    if(!(projectSkill.getProjectMap().getName().equals(getProjectMap().getName())))
	        return false;
	    
	    if(!(getSkillMap().getName().equals(projectSkill.getSkillMap().getName())))
	        return false;


	   return true;
	}


}
