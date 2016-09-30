package project.planner.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import project.planner.dto.ProjectDetails;
import project.planner.model.Employee;
import project.planner.model.EmployeeProject;
import project.planner.model.Project;
import project.planner.model.ProjectSkill;
import project.planner.model.Skill;
import project.planner.model.Status;
import project.planner.repository.EmployeeRepository;
import project.planner.repository.ProjectRepository;
import project.planner.repository.ProjectSkillRepository;
import project.planner.repository.SkillRepository;
import project.planner.util.Constants;
import project.planner.util.UserDataFileProcessor;

/**
 * This class has the core logic. The Projects are created in Project Entity
 * (project table). The class assigns the Employees to Projects. Method
 * "assignProject" has the logic of assigning Projects to Employees. The
 * assignProject has the constant time performance of O(n).
 * 
 */
@Controller
public class ProjectController {

	final static Logger logger = Logger.getLogger(ProjectController.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private ProjectSkillRepository projectSkillRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	EntityManager em;

	/**
	 * The Method creates the Projects and the required skills in the project
	 * and project_skill table.
	 * 
	 * @param projectDetailsList
	 * @param startDate
	 */
	@Transactional
	public void createProjectDetails(List<ProjectDetails> projectDetailsList, String startDate) {

		for (ProjectDetails projectDetails : projectDetailsList) {

			Project project = new Project();

			project.setName(projectDetails.getName());
			project.setDurationInWeeks(projectDetails.getDuration());
			project.setStatus(Status.NEW);
			Map<String, String> projectSkills = projectDetails.getProjectSkills();

			Set<String> eachProjectSkills = projectSkills.keySet();

			projectSkills.put(Constants.QA, "1");
			projectSkills.put(Constants.OPS, "1");

			Iterator<String> iterator = eachProjectSkills.iterator();
			Set<ProjectSkill> projectSkillDetailSet = new HashSet<ProjectSkill>();

			while (iterator.hasNext()) {
				ProjectSkill projectSkillDetail = new ProjectSkill();
				String projectSkill = iterator.next();
				Skill skill = skillRepository.findByName(projectSkill.trim());
				if (null == skill) {
					skill = new Skill();
					skill.setName(projectSkill.trim());
					skillRepository.save(skill);
				}
				projectSkillDetail.setQuantity(projectSkills.get(projectSkill.trim()));
				projectSkillDetail.setSkillMap(skill);
				projectSkillDetail.setProjectMap(project);
				project.getProjectSkills().add(projectSkillDetail);
				projectSkillDetailSet.add(projectSkillDetail);
			}
			projectRepository.save(project);
			projectSkillRepository.save(projectSkillDetailSet);
		}
	}

	/**
	 * The Method creates the Projects and the required skills in the project
	 * and project_skill table.
	 * 
	 * @param projectDetailsList
	 * @param startDate
	 */
	@Transactional
	public List<Project> assignProjects() {

		List<Project> projects = projectRepository.findAll(new Sort(Sort.Direction.ASC, Constants.DUR_IN_WEEKS));

		return assignProject(projects);
	}

	/**
	 * The method assigns Employees to Projects and also assigns the Project
	 * start and end date. If the project can't be started, proper error message
	 * is written to the Project.
	 * 
	 * The Method has a performance of linear time complexity: O(n)
	 * 
	 * @param projectDetailsList
	 * @param startDate
	 */
	@SuppressWarnings("serial")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Project> assignProject(List<Project> projects) {

		for (Project project : projects) {

			Set<ProjectSkill> projectSkills = project.getProjectSkills();
			Set<String> skillsProject = new HashSet<String>();
			int durationInWeeks = project.getDurationInWeeks();
			int projectStartWeek = Constants.MAX_WEEKS;
			String skillShortage = "";
			String empNames = "";

			try {
				for (ProjectSkill projectSkill : projectSkills) {

					String skillName = projectSkill.getSkillMap().getName();
					skillsProject.add(skillName);
				}
			} catch (Exception exp) {
				logger.info(exp);
			}

			List<Employee> employees = new ArrayList<Employee>();

			employees = employeeRepository.availableEmployees(skillsProject);

			boolean resourceScarcityFlag = false;
			Set<EmployeeProject> employeeProjectSet = new HashSet<EmployeeProject>();

			Map<String, List<Employee>> employeeSkillSet = new HashMap<String, List<Employee>>();

			for (final Employee emp : employees) {

				final String skillName = emp.getEmployeeSkills().iterator().next().getSkill().getName();

				if (employeeSkillSet.containsKey(skillName)) {
					final List<Employee> empBySkill = employeeSkillSet.get(skillName);
					empBySkill.add(emp);
					employeeSkillSet.put(skillName, empBySkill);
				} else {
					employeeSkillSet.put(skillName, new ArrayList<Employee>() {
						{
							add(emp);
						}
					});
				}

			}

			for (ProjectSkill projectSkill : projectSkills) {

				int quantity = Integer.valueOf(projectSkill.getQuantity());
				String skillName = projectSkill.getSkillMap().getName();
				skillName = null == skillName ? "" : skillName;

				List<Employee> employeeSkills = employeeSkillSet.get(skillName);

				if (null == employeeSkills) {
					resourceScarcityFlag = true;
					break;
				}

				for (Employee emp : employeeSkills) {

					int remainingCap = emp.getCapacityInWeeks() - durationInWeeks;

					EmployeeProject employeeProjectDetail = new EmployeeProject();
					employeeProjectDetail.setProject(project);
					employeeProjectDetail.setProjectName(project.getName());
					employeeProjectDetail.setEmployee(emp);
					if (emp.getCapacityInWeeks() < projectStartWeek) {
						projectStartWeek = emp.getCapacityInWeeks();
					}

					employeeProjectDetail.getEmployee().setCapacityInWeeks(remainingCap);
					employeeProjectSet.add(employeeProjectDetail);
					quantity--;

					if (quantity == 0) {
						empNames = empNames + emp.getName() + "(" + skillName + ")" + ", ";
						break;
					}
				}

				if (quantity != 0) {
					skillShortage += skillName + " ,";
					resourceScarcityFlag = true;
					break;

				}

			}

			int startDate = Constants.MAX_WEEKS - (projectStartWeek - 1);
			int endDate = startDate + durationInWeeks - 1;

			if (resourceScarcityFlag) {
				for (EmployeeProject employeeProject : employeeProjectSet) {
					int capacity = employeeProject.getEmployee().getCapacityInWeeks();
					employeeProject.getEmployee().setCapacityInWeeks(capacity + durationInWeeks);
				}
				if (skillShortage.isEmpty()) {
					project.setComments(Constants.SKILL_SCARCITY);
				} else {
					project.setComments(Constants.ZERO_SKILLS + skillShortage.substring(0, skillShortage.length() - 1));
				}

				project.setStatus(Status.DEFERRED);
				logger.info(project.getName());
				logger.info(Constants.NOT_FEASIBLE + project.getComments());
				logger.info(" ");

				continue;
			} else if (endDate > Constants.MAX_WEEKS) {
				for (EmployeeProject employeeProject : employeeProjectSet) {
					int capacity = employeeProject.getEmployee().getCapacityInWeeks();
					employeeProject.getEmployee().setCapacityInWeeks(capacity + durationInWeeks);
				}

				project.setComments(Constants.NO_TIME);
				project.setStatus(Status.DEFERRED);
				logger.info(project.getName());
				logger.info(Constants.NOT_FEASIBLE + project.getComments());
				logger.info(" ");
				continue;
			} else {
				project.setStartDateWeek(startDate);
				project.setEndDateWeek(startDate + durationInWeeks - 1);
				project.setStatus(Status.ACTIVE);
				project.getEmployeeProjects().addAll(employeeProjectSet);
				projectRepository.save(project);

				logger.info(project.getName());
				logger.info(Constants.START_DATE + UserDataFileProcessor.weekDate(startDate));
				logger.info(Constants.END_DATE + UserDataFileProcessor.weekDate(startDate + durationInWeeks - 1));
				logger.info(Constants.TEAM_NAMES + empNames.substring(0, empNames.length() - 2));
				logger.info(" ");

			}

		}

		return projects;

	}

}
