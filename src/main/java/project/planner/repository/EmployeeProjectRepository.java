package project.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.planner.model.EmployeeProject;

/**
 * EmployeeProject interface to store the EmployeeProject details.
 */
@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long>{


}
