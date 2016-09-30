package project.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.planner.model.ProjectSkill;

/**
 * ProjectSkill repository to store the ProjectSkill details.
 */
@Repository
public interface ProjectSkillRepository extends JpaRepository<ProjectSkill, Long>{

}
