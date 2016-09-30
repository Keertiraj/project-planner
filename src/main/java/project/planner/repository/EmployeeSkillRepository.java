package project.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.planner.model.EmployeeSkill;
/**
 * EmployeeSkill repository to store the EmployeeSkill details.
 */
@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long>{
	
 
}
