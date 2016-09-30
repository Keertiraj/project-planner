package project.planner.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Skill Entity class to store the skill details.
 */
@Entity
@Table(indexes = {
		@Index(columnList = "name", unique=true)})
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

    @OneToMany(mappedBy = "skill",cascade={CascadeType.REFRESH, CascadeType.MERGE})
	private Set<EmployeeSkill> employeeSkills;

    @OneToMany(mappedBy = "skillMap",cascade={CascadeType.REFRESH, CascadeType.MERGE})
	private Set<ProjectSkill> projectSkills;
	 
	
	public Set<ProjectSkill> getProjectSkills() {
		return projectSkills;
	}

	public void setProjectSkills(Set<ProjectSkill> projectSkills) {
		this.projectSkills = projectSkills;
	}


	public Skill() {
	}

	public Skill(String name) {
		this.name = name;
	}

	public Skill(String name, Set<Employee> employees) {
		this.name = name;
	}

	public Set<EmployeeSkill> getEmployeeSkills() {
		return employeeSkills;
	}

	public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
		this.employeeSkills = employeeSkills;
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
	
	@Override
	public String toString() {
		String result = String.format("Skill [id=%d, name='%s']%n", id, name);
		if (employeeSkills != null) {
			for (EmployeeSkill employeeSkill : employeeSkills) {
				result += String.format("Skill[id=%d, name='%s']%n", employeeSkill.getId(), employeeSkill.getSkillName());
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	        return false;

	    if(!(o instanceof Skill))
	        return false;

	    if(getName() == null)
	        return false;

	    Skill skill = (Skill) o;
	    if(!(getName().equals(skill.getName())))
	        return false;
	    
		   return true;
	}


}
