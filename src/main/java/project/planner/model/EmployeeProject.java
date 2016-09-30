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
 * EmployeeProject Entity class to store the Employee's Project details.
 */
@Entity
@Table(name = "employee_project")
public class EmployeeProject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	private String projectName;

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}	
	
	@Override
	public String toString() {
		String result = String.format("Employee  Project[id=%d, project='%s']%n", id, employee);
				result += String.format("Skill[id=%d, name='%s']%n", project.getId(), project.getName());
			
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	        return false;

	    if(!(o instanceof EmployeeProject))
	        return false;

	    if(getProjectName() == null)
	        return false;

	    EmployeeProject employeeProject = (EmployeeProject) o;
	    if(!(getProjectName().equals(employeeProject.getProjectName())))
	        return false;
	    
	    if(!(getEmployee().getName().equals(employeeProject.getEmployee().getName())))
	        return false;


	   return true;
	}

}
