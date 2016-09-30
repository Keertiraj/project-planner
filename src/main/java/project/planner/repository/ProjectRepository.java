package project.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.planner.model.Project;

/**
 * Project repository to store the Project details.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
}
