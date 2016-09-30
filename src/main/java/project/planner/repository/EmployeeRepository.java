package project.planner.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.planner.model.Employee;

/**
 * Employee repository to store the Employee details.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query("SELECT e1 from Employee e1 , EmployeeSkill es where es.employee.id = e1.id and  es.skillName IN (:skillsProject) and e1.capacityInWeeks > 0 ORDER BY e1.capacityInWeeks DESC")
    List<Employee> availableEmployees(@Param("skillsProject") Set<String> skillsProject);
	
}
 