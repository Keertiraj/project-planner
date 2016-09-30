package project.planner;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.planner.controllers.EmployeeController;
import project.planner.controllers.ProjectController;
import project.planner.dto.Response;
import project.planner.exceptions.UserDataException;
import project.planner.util.UserDataFileProcessor;
import project.planner.util.Constants;

/**
 * SpringBoot Class. Following class is invoked when the application starts.
 * Input file can be passed in as parameter or System.in stream.
 * 
 */
@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {

	final static Logger logger = Logger.getLogger(HelloJpaApplication.class);

	@Autowired
	private EmployeeController employeeCtrl;

	@Autowired
	private ProjectController projectCtrl;

	/**
	 * The method receives the input file, parses the input file, checks for
	 * error and invokes the run method of CommandLineRunner class.
	 * 
	 * @param args
	 *            - The input parameter passed in as file.
	 * @throws UserDataException
	 *             - throws UserData Exception in case of invalid input file or
	 *             invalid format.
	 */
	public static void main(String[] args) throws UserDataException {

		BufferedReader reader = null;
		String startDate = "";
		logger.info(Constants.COMMAND_LINE_FORMAT);

		try {
			if (args.length == 1) {
				String fileName = args[0];
				reader = new BufferedReader(new FileReader(fileName));
				UserDataFileProcessor.dateFormatter("");
			} else if (args.length == 2) {

				try {
					startDate = args[1];
					UserDataFileProcessor.dateFormatter(startDate);
					String fileName = args[0];
					reader = new BufferedReader(new FileReader(fileName));

				} catch (Exception exp) {
					logger.error(Constants.INCORRECT_DATE_FORMAT);
					return;
				}

			} else {
				reader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
			}

		} catch (Exception exp) {

			logger.error(Constants.INCORRECT_PATH);
			return;
		}

		try {
			final Response response = UserDataFileProcessor.parseFile(reader);

			UserDataFileProcessor.checkForError(response.getError());

			startDate = startDate == null ? "" : startDate;

			logger.info(Constants.BOOT_APP_MSG);

			SpringApplication.run(HelloJpaApplication.class, response.getResponse(), startDate);
		} catch (Exception exp) {

			logger.error(Constants.GENERIC_ERROR_MSG);
			return;
		}

	}

	/**
	 * The method receives receives the parsed input file, process Employee
	 * Data, check for error in employee data, create skills for employees,
	 * create Projects and assign projects to employees. Display the Project
	 * plan details on the console
	 * 
	 * @param args
	 *            - The input parameter passed in as parsed file.
	 * @throws Exception
	 *             - throws Exception in case of invalid Employee data or
	 */
	public void run(String... args) throws Exception {

		String startDate = "";
		String inputFile = "";

		if (args.length > 1) {

			inputFile = args[0];
			startDate = args[1];

		} else {

			startDate = args[1];
		}
		
		final Response userDataFormatted = UserDataFileProcessor.processEmployeesData(inputFile);

		UserDataFileProcessor.checkForError(userDataFormatted.getError());

		// Persist Employee and Skill Details.
		employeeCtrl.createEmployeeSkillDetails(userDataFormatted.getEmployeesSkills(), startDate);

		// Persist Project Details
		projectCtrl.createProjectDetails(userDataFormatted.getProjects(), startDate);

		// Assign the Project to Employees
		projectCtrl.assignProjects();

	}
}
