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
 * EmployeeSkill Entity class to store the Employee's Skill details.
 */
@Entity
@Table(name = "employee_skill")
public class EmployeeSkill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "skill_id")
	private Skill skill;

	private String skillName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	@Override
	public String toString() {
		String result = String.format("Employee  Skill[id=%d, Employee='%s']%n", id, employee);
				result += String.format("Skill[id=%d, name='%s']%n", skill.getId(), skill.getName());
			
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	        return false;

	    if(!(o instanceof EmployeeSkill))
	        return false;

	    if(getSkillName() == null)
	        return false;

	    EmployeeSkill employeeSkill = (EmployeeSkill) o;
	    if(!(getSkillName().equals(employeeSkill.getSkillName())))
	        return false;
	    
	    if(!(getEmployee().getName().equals(employeeSkill.getEmployee().getName())))
	        return false;


	   return true;
	}


}
