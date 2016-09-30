package project.planner.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import project.planner.dto.ProjectDetails;
import project.planner.dto.Response;
import project.planner.exceptions.UserDataException;

/**
 * This is an utility class to perform repeated functionalities
 */
public class UserDataFileProcessor {

	final static Logger logger = Logger.getLogger(UserDataFileProcessor.class);

	/**
	 * The method reads the BufferReader. It checks the input parameters for the
	 * valid inputs. If there are invalid data/characters, the error response is
	 * generated.
	 * 
	 * @param reader
	 * @return Response
	 */
	public static Response parseFile(BufferedReader reader) {

		String line = null;
		StringBuffer strBuffer = new StringBuffer();
		Response response = new Response();

		try {

			boolean flag = false;
			boolean appendSeparator = false;

			while ((line = reader.readLine()) != null) {

				flag = true;

				if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
					strBuffer.append(" ");
				} else {
					strBuffer.append(" " + line);
					if (appendSeparator) {
						strBuffer.append(Constants.SEPARATOR_CHARS);
					}
				}

				if (Constants.PROJECTS.equalsIgnoreCase(line)) {
					appendSeparator = true;
				}

			}

			if (!flag) {
				response.setError(Constants.FILE_PROCESS_FAILURE_MSG);
				response.setResponse("");
				return response;
			}

		} catch (Exception e) {

			response.setError(Constants.INPUT_FILE_PROCESS_ERROR);

		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					String error = response.getError() == null ? "" : response.getError();
					error = error + Constants.EMPTY_FILE;
					response.setError(error);
				}
		}

		String inputFileStr = strBuffer.toString();
		Pattern regex = Pattern.compile(Constants.ILLEGAL_CHARS);
		Matcher matcher = regex.matcher(inputFileStr);
		if (matcher.find()) {
			response.setError(Constants.ILLEGAL_CHARS_MSG);
		}

		response.setResponse(inputFileStr);

		return response;
	}

	/**
	 * The method reads the Project details. It checks the input parameters for
	 * the valid inputs. If there are invalid data/characters, the error
	 * response is generated.
	 * 
	 * @param reader
	 * @return Response
	 */
	public static Response processEmployeesData(String userInput) {

		Response response = new Response();
		String[] peopleProjects = userInput.split(Constants.FILE_DELIMITERS);
		Map<String, String> employeesSkills = new HashMap<String, String>();
		List<ProjectDetails> projects = new ArrayList<ProjectDetails>();
		String[] employees = new String[] {};

		if (null == peopleProjects || peopleProjects.length < 2) {
			response.setError(Constants.WRONG_FORMAT_FILE);
			return response;
		}

		employees = peopleProjects[1].trim().split(" ");

		for (String empoyee : employees) {

			String[] employeeSkills = empoyee.trim().split(":");
			if (employeeSkills.length < 2) {
				response.setError(Constants.INVALID_USER_INPUT);
				return response;
			}
			employeesSkills.put(employeeSkills[0].trim(), employeeSkills[1].trim());

		}

		response.setEmployeesSkills(employeesSkills);

		String[] allProjects = peopleProjects[2].trim().split(Constants.SEPARATOR_CHARS);

		for (String projectDetail : allProjects) {

			ProjectDetails project = new ProjectDetails();

			Pattern pattern = Pattern.compile(Constants.PROJECT_DATA_VALID_REGEX,
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

			Matcher matcher = pattern.matcher(projectDetail);

			if (matcher.find()) {

				project.setName(matcher.group(1).trim());
				project.setDuration(Integer.parseInt(matcher.group(7).trim()));

				String[] resources = matcher.group(3).trim().split(",");

				Map<String, String> projectSkills = new HashMap<String, String>();

				for (String resource : resources) {

					String[] resourceQnt = resource.trim().split("x");
					projectSkills.put(resourceQnt[1].trim(), resourceQnt[0].trim());

				}
				project.setProjectSkills(projectSkills);
			} else {
				response.setError(Constants.INVALID_PROJECT_INPUT);
			}
			projects.add(project);

		}

		response.setProjects(projects);

		return response;

	}

	/**
	 * The method checks for the error logs it accordingly.
	 * 
	 * @param error
	 * @throws UserDataException
	 */
	public static void checkForError(String error) throws UserDataException {

		if (!error.isEmpty()) {
			logger.error(Constants.ERROR + error);
			throw new UserDataException(error);
		}
	}

	/**
	 * The method calculates the remaining weeks for an employee for a project.
	 * 
	 * @param datecurr
	 * @return int
	 */
	public static int remainingWeeks(String datecurr) {
		Date date = null;
		try {
			date = dateFormatter(datecurr);
		} catch (Exception e) {

			logger.error(Constants.DATE_FORMAT_ERROR);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int week = cal.get(Calendar.WEEK_OF_MONTH);
		week = week > 4 ? 4 : week;
		int totalWeeks = 48 - (month * 4 + week);
		return totalWeeks;
	}

	/**
	 * Converting String to Date Object
	 * 
	 * @param date
	 * @return Date
	 * @throws Exception
	 */
	public static Date dateFormatter(String date) throws Exception {

		DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date dateCurr = null;
		if (date.isEmpty()) {
			dateCurr = new Date();
		} else {
			dateCurr = formatter.parse(date);
		}

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);

		if (dateCurr.before(today.getTime())) {
			throw new Exception(Constants.PAST_DATE);
		}

		return dateCurr;
	}

	/**
	 * The method converts week into month and week
	 * 
	 * @param int
	 * @return String
	 */
	public static String weekDate(int week) {

		int month = week / 4;
		int weekRemaining = week % 4;

		if (weekRemaining == 0) {
			month--;
			weekRemaining = 4;
		}

		Months lg = Months.valueOf(month);

		return weekOf(weekRemaining) + " " + lg.name();
	}

	/**
	 * Method resturns the String representation of the week
	 * 
	 * @param weekVal
	 * @return String
	 */
	public static String weekOf(int weekVal) {

		String weekEqu = "";

		if (weekVal == 1) {

			weekEqu = Constants.FIRST_WEEK;
		} else if (weekVal == 2) {

			weekEqu = Constants.SECOND_WEEK;
		} else if (weekVal == 3) {

			weekEqu = Constants.THIRD_WEEK;
		} else if (weekVal == 4) {

			weekEqu = Constants.FOURTH_WEEK;
		}

		return weekEqu;

	}

}
