package project.planner.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Employee Entity class to store the employee details.
 */

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	private int capacityInWeeks;

	@OneToMany(mappedBy = "employee", cascade={CascadeType.REFRESH, CascadeType.MERGE},fetch=FetchType.EAGER)
	private Set<EmployeeSkill> employeeSkills;

	@OneToMany(mappedBy = "employee")
	private Set<EmployeeProject> employeeProjects;

	public Employee() {
		employeeSkills = new HashSet<>();

	}

	public Employee(String name) {
		this.name = name;
		employeeSkills = new HashSet<>();
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

	public int getCapacityInWeeks() {
		return capacityInWeeks;
	}

	public void setCapacityInWeeks(int capacityInWeeks) {
		this.capacityInWeeks = capacityInWeeks;
	}
	
	public Set<EmployeeProject> getEmployeeProjects() {
		return employeeProjects;
	}

	public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
		this.employeeProjects = employeeProjects;
	}

	public Set<EmployeeSkill> getEmployeeSkills() {
		return employeeSkills;
	}

	public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
		this.employeeSkills = employeeSkills;
	}

	@Override
	public String toString() {
		String result = String.format("Employee  Skill[id=%d, name='%s']%n", id, name);
		if (employeeSkills != null) {
			for (EmployeeSkill skill : employeeSkills) {
				result += String.format("Skill[id=%d, name='%s']%n", skill.getId(), skill.getSkillName());
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	        return false;

	    if(!(o instanceof Employee))
	        return false;

	    if(getName() == null)
	        return false;

	    Employee employee = (Employee) o;
	    if(!(getName().equals(employee.getName())))
	        return false;

	   return true;
	}

}
