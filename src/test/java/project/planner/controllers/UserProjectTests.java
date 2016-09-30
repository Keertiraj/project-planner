package project.planner.controllers;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import project.planner.dto.Response;
import project.planner.exceptions.UserDataException;
import project.planner.model.Project;
import project.planner.model.Status;
import project.planner.repository.EmployeeProjectRepository;
import project.planner.repository.EmployeeRepository;
import project.planner.repository.EmployeeSkillRepository;
import project.planner.repository.ProjectRepository;
import project.planner.repository.ProjectSkillRepository;
import project.planner.repository.SkillRepository;
import project.planner.util.Constants;
import project.planner.util.UserDataFileProcessor;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * Integration Tests for various Scenarios
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/spring-config.xml")
public class UserProjectTests {

	@Autowired
	private EmployeeController employeeCtrl;

	@Autowired
	private ProjectController projectCtrl;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	EmployeeProjectRepository employeeProjectRepository;

	@Autowired
	ProjectSkillRepository projectSkillRepository;

	@Autowired
	EmployeeSkillRepository employeeSkillRepository;

	/**
	 * All the Projects are assigned for the given year.
	 * Input data:

			PEOPLE
			Carl:Java
			Lenny:Java
			Bart:JavaScript
			Moe:JavaScript
			Milhouse:.net
			Lisa:.net
			Ned:QA
			Edna:QA
			Barney:Ops
			Homer:Ops
			Homer1:Ruby
			Homer2:Ruby
			Homer3:Ruby
			bob:QA
			
			PROJECTS
			PIM (2x.net, 1xJavaScript) - 35 weeks
			XYZ (1xRuby) - 1 weeks
			ABC (2x.net, 1xJavaScript) - 10 weeks
			DEF (2xRuby) - 21 weeks
			SEF (2xJava) - 4 weeks

	 * @throws UserDataException
	 */
	@Test
	public void testAllProjectsAssigned() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor
				.processEmployeesData(Constants.USER_INPUT_ALL_PROJECTS_ASSIGNED);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());

		// Persist Employee and Skill Details.
		employeeCtrl.createEmployeeSkillDetails(userDataFormatted.getEmployeesSkills(), "");

		// Persist Project Details
		projectCtrl.createProjectDetails(userDataFormatted.getProjects(), "");

		// Assign the Project to Employees
		List<Project> projects = projectCtrl.assignProjects();

		for (Project project : projects) {

			assertEquals(project.getStatus(), Status.ACTIVE);

			if (Constants.PROJECT_NAME_PIM.equals(project.getName())) {
				assertEquals(project.getDurationInWeeks(), 35);
			}

		}

		assertEquals(projects.size(), 5);
		assertTrue(StringUtils.isEmpty(userDataFormatted.getError()));

	}

	/**
	 * One Project is not assigned due to insufficient skill(s).
	 * Input data:

			PEOPLE
			Carl:Java
			Lenny:Java
			Bart:JavaScript
			Moe:JavaScript
			Milhouse:.net
			Lisa:.net
			Ned:QA
			Edna:QA
			Barney:Ops
			Homer:Ops
			Homer1:Ruby
			Homer2:Ruby
			Homer3:Ruby
			bob:QA
			
			PROJECTS
			PIM (2x.net, 1xJavaScript) - 35 weeks
			DAM (3xJava, 2xJavaScript) - 5 weeks
			XYZ (1xRuby) - 1 weeks
			ABC (2x.net, 1xJavaScript) - 10 weeks
			DEF (2xRuby) - 21 weeks
			SEF (2xJava) - 4 weeks

	 * @throws UserDataException
	 */

	@Test
	public void testValidUsersAndNoSKillInputFile() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor.processEmployeesData(Constants.USER_INPUT_VALID_FILE);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());

		// Persist Employee and Skill Details.
		employeeCtrl.createEmployeeSkillDetails(userDataFormatted.getEmployeesSkills(), "");

		// Persist Project Details
		projectCtrl.createProjectDetails(userDataFormatted.getProjects(), "");

		// Assign the Project to Employees
		List<Project> projects = projectCtrl.assignProjects();

		assertEquals(projects.size(), 6);
		assertTrue(StringUtils.isEmpty(userDataFormatted.getError()));

		for (Project project : projects) {

			if (Constants.PROJECT_NAME_DAM.equals(project.getName())) {
				assertEquals(project.getComments().trim(), Constants.ZERO_SKILLS + Constants.PROJECT_SKILL_JAVA);
				assertEquals(project.getStatus(), Status.DEFERRED);

			} else {
				assertEquals(project.getStatus(), Status.ACTIVE);
			}

		}

	}

	/**
	 * One Projects is not assigned as it can't be completed in the given year.
	 * Input data:

			PEOPLE
			Carl:Java
			Lenny:Java
			Bart:JavaScript
			Moe:JavaScript
			Milhouse:.net
			Lisa:.net
			Ned:QA
			Edna:QA
			Barney:Ops
			Homer:Ops
			Homer1:Ruby
			Homer2:Ruby
			Homer3:Ruby
			bob:QA
			
			PROJECTS
			PIM (2x.net, 1xJavaScript) - 35 weeks
			DAM (2xJava, 2xJavaScript) - 45 weeks
			XYZ (1xRuby) - 1 weeks
			ABC (2x.net, 1xJavaScript) - 10 weeks
			DEF (2xRuby) - 21 weeks
			SEF (2xJava) - 4 weeks

	 * @throws UserDataException
	 */
	@Test
	public void testValidUsersAndLongerProjectInputFile() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor
				.processEmployeesData(Constants.USER_INPUT_PROJECTS_LONGER);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());

		// Persist Employee and Skill Details.
		employeeCtrl.createEmployeeSkillDetails(userDataFormatted.getEmployeesSkills(), "");

		// Persist Project Details
		projectCtrl.createProjectDetails(userDataFormatted.getProjects(), "");

		// Assign the Project to Employees
		List<Project> projects = projectCtrl.assignProjects();

		assertEquals(projects.size(), 5);
		assertTrue(StringUtils.isEmpty(userDataFormatted.getError()));

		for (Project project : projects) {

			if (Constants.PROJECT_NAME_XYZ.equals(project.getName())) {
				assertEquals(project.getComments().trim(), Constants.NO_TIME);
				assertEquals(project.getStatus(), Status.DEFERRED);

			} else {
				assertEquals(project.getStatus(), Status.ACTIVE);
			}

		}

	}

	/**
	 * All the projects are assigned and the project is planned as per the given future date.
	 * 
	 * Input data:

			PEOPLE
			Carl:Java
			Lenny:Java
			Bart:JavaScript
			Moe:JavaScript
			Milhouse:.net
			Lisa:.net
			Ned:QA
			Edna:QA
			Barney:Ops
			Homer:Ops
			Homer1:Ruby
			Homer2:Ruby
			Homer3:Ruby
			bob:QA
			
			PROJECTS
			PIM (2x.net, 1xJavaScript) - 35 weeks
			XYZ (1xRuby) - 1 weeks
			ABC (2x.net, 1xJavaScript) - 10 weeks
			DEF (2xRuby) - 21 weeks
			SEF (2xJava) - 4 weeks

	 * @throws UserDataException
	 */
	@Test
	public void testAllProjectsAssigneduFutureDate() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor
				.processEmployeesData(Constants.USER_INPUT_ALL_PROJECTS_ASSIGNED);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());

		// Persist Employee and Skill Details.
		employeeCtrl.createEmployeeSkillDetails(userDataFormatted.getEmployeesSkills(), "01-01-2017");

		// Persist Project Details
		projectCtrl.createProjectDetails(userDataFormatted.getProjects(), "");

		// Assign the Project to Employees
		List<Project> projects = projectCtrl.assignProjects();

		for (Project project : projects) {

			assertEquals(project.getStatus(), Status.ACTIVE);

			if (Constants.PROJECT_NAME_PIM.equals(project.getName())) {
				assertEquals(project.getDurationInWeeks(), 35);
			}

		}

		assertEquals(projects.size(), 5);
		assertTrue(StringUtils.isEmpty(userDataFormatted.getError()));

	}

	/**
	 * Error Message returned as the input file has special characters.
	 * 
	 * Input data:

		PEOPLE
		Carl:Java
		Lenny:Java
		Bart:Java~Script
		Moe:JavaScript
		Milhouse:.net
		Lisa:.net
		Ned:QA
		Edna:QA
		Barney:Ops
		Homer:Ops
		Homer1:Ruby
		Homer2:Ruby
		Homer3:Ruby
		bob:QA
		
		PROJ<ECTS
		PIM (2x.net, 1xJavaScript) - 35 weeks
		XYZ (2^xJava, 2xJavaScript)
		DAM (1xRu%by) - 1 weeks
		ABC (2x.net, 1xJavaScript) - 10 weeks
		DEF (2xRuby) - 21 weeks
		SEF (2xJava) - 4 weeks


	 * @throws UserDataException
	 */
	@Test
	public void testinValidInputFileSpecialCharacters() throws UserDataException {
		
		InputStream is = new ByteArrayInputStream(Constants.USER_INPUT_INVALID_FILE_SPECIAL_CHARS.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		final Response userDataFormatted = UserDataFileProcessor.parseFile(br);

		assertTrue(userDataFormatted.getError().contains(Constants.ILLEGAL_CHARS_MSG));
	}
	
	/**
	 * Empty input Stream
	 * @throws UserDataException
	 */
	@Test
	public void testEmptyInputStream() throws UserDataException {
		
		InputStream is = new ByteArrayInputStream("".getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		final Response userDataFormatted = UserDataFileProcessor.parseFile(br);

		assertTrue(userDataFormatted.getError().contains(Constants.FILE_PROCESS_FAILURE_MSG));
	}

	/**
	 * Error Message returned as the input file has invalid user name and skill format.
	 * 
	 * Input data:

		PEOPLE
		CarlJava
		Lenny:Java
		Bart:JavaScript
		Moe:JavaScript
		Milhouse:.net
		Lisa:.net
		Ned:QA
		Edna:QA
		Barney:Ops
		Homer:Ops
		Homer1:Ruby
		Homer2:Ruby
		Homer3:Ruby
		bob:QA
		
		PROJECTS
		PIM (2x.net, 1xJavaScript) - 35 weeks
		XYZ (1xRuby) - 1 weeks
		ABC (2x.net, 1xJavaScript) - 10 weeks
		DEF (2xRuby) - 21 weeks
		SEF (2xJava) - 4 weeks

	 * @throws UserDataException
	 */
	@Test
	public void testinValidInputFile() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor
				.processEmployeesData(Constants.USER_INPUT_INVALID_FILE_INVALID_INPUT2);

		assertTrue(userDataFormatted.getError().contains(Constants.INVALID_USER_INPUT_NO_NEWLINE));
	}

	/**
	 * Empty File User Input
	 * 
	 * @throws UserDataException
	 */
	@Test
	public void testinValidInputEmptyFile() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor.processEmployeesData("");

		assertFalse(StringUtils.isEmpty(userDataFormatted.getError()));
	}

	/**
	 * Empty File User Input
	
		 Input Data
		 
		 PROJECTS
		 PIM (2x.net, 1xJavaScript) - 35 weeks
		 XYZ (1xRuby) - 1 weeks
		 ABC (2x.net, 1xJavaScript) - 10 weeks
		 DEF (2xRuby) - 21 weeks
		 SEF (2xJava) - 4 weeks

	 * @throws UserDataException
	 */
	@Test(expected = UserDataException.class)
	public void testinValidInputFileNoPeople() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor
				.processEmployeesData(Constants.USER_INPUT_INVALID_FILE_NO_PEOPLE);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());
	}

	/**
	 * Empty File Projects Input
	
		Input Data
		 
		PEOPLE
		CarlJava
		Lenny:Java
		Bart:JavaScript
		Moe:JavaScript
		Milhouse:.net
		Lisa:.net
		Ned:QA
		Edna:QA
		Barney:Ops
		Homer:Ops
		Homer1:Ruby
		Homer2:Ruby
		Homer3:Ruby
		bob:QA

	 * @throws UserDataException
	 */

	@Test(expected = UserDataException.class)
	public void testinValidInputFileNoProjects() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor
				.processEmployeesData(Constants.USER_INPUT_INVALID_FILE_NO_PROJECTS);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());
	}

	/**
	 * Empty File User Input. Exception Scenario.
	 * 
	 * @throws UserDataException
	 */

	@Test(expected = UserDataException.class)
	public void testinValidInputFileExceptionThrown() throws UserDataException {

		final Response userDataFormatted = UserDataFileProcessor.processEmployeesData("");

		UserDataFileProcessor.checkForError(userDataFormatted.getError());
	}
	
	/**
	 * Date Formatter class. Exception Scenario.
	 * 
	 * @throws UserDataException
	 */
	@Test(expected = Exception.class)
	public void testDateFormatter() throws Exception {
		
		UserDataFileProcessor.dateFormatter(null);
	}

	/**
	 * Remaining Weeks class. Exception Scenario.
	 * 
	 * @throws UserDataException
	 */
	@Test(expected = Exception.class)
	public void testRemainingWeeks() throws UserDataException {
		
		UserDataFileProcessor.remainingWeeks(null);
	}


	@After
	public void tearDown() {

		employeeProjectRepository.deleteAll();
		projectSkillRepository.deleteAll();
		employeeSkillRepository.deleteAll();
		projectRepository.deleteAll();
		employeeRepository.deleteAll();
		skillRepository.deleteAll();

	}
}