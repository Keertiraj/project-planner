package project.planner.controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import project.planner.model.Employee;
import project.planner.model.EmployeeSkill;
import project.planner.model.Skill;
import project.planner.repository.EmployeeRepository;
import project.planner.repository.EmployeeSkillRepository;
import project.planner.repository.SkillRepository;
import project.planner.util.UserDataFileProcessor;
import project.planner.util.Constants;

/**
 * The Controller creates the Employees and their skills in the database.
 * Employee details are stored in Employee Entity (employee table) and their
 * corresponding skills are stored in EmployeeSkill (employee_skill) table
 * through many to many relationship.
 * 
 */

@Controller
public class EmployeeController {

	final static Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private EmployeeSkillRepository employeeSkillRepository;

	/**
	 * The method creates the employee and their corresponding skills in the
	 * database(employee table and employee_skill) through many to many
	 * relationship.
	 * 
	 * @param employeesSkills
	 * @param startDate
	 */
	@Transactional
	public void createEmployeeSkillDetails(Map<String, String> employeesSkills, String startDate) {

		final Set<String> employees = employeesSkills.keySet();

		for (String employee : employees) {

			String skillset = employeesSkills.get(employee);
			String[] skillSetsEmployee = skillset.split(",");

			Employee employeeDetails = new Employee(employee);

			int remainingWeeks = 0;

			if (null == startDate || startDate.isEmpty()) {

				remainingWeeks = Constants.MAX_WEEKS;
			} else {
				remainingWeeks = UserDataFileProcessor.remainingWeeks(startDate);
			}

			employeeDetails.setCapacityInWeeks(remainingWeeks);

			if (null != skillSetsEmployee && skillSetsEmployee.length > 0) {

				Set<EmployeeSkill> employeeSkillDetailSet = new HashSet<EmployeeSkill>();

				for (String skill : skillSetsEmployee) {

					EmployeeSkill employeeSkillDetail = new EmployeeSkill();
					Skill existingSkills = skillRepository.findByName(skill);

					if (null == existingSkills) {
						existingSkills = new Skill();
						existingSkills.setName(skill.trim());
						skillRepository.save(existingSkills);
					}

					employeeSkillDetail.setSkill(existingSkills);
					employeeSkillDetail.setEmployee(employeeDetails);
					employeeSkillDetail.setSkillName(skill.trim());
					employeeDetails.getEmployeeSkills().add(employeeSkillDetail);
					employeeSkillDetailSet.add(employeeSkillDetail);
				}

				employeeRepository.save(employeeDetails);
				employeeSkillRepository.save(employeeSkillDetailSet);

			}

		}

	}

}
