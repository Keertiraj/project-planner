package project.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.planner.model.Skill;

/**
 * Skill repository to store the Skill details.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

	Skill findByName(String name);

}
