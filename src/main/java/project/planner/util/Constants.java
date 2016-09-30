package project.planner.util;

/**
 * Class to store static Constants
 *
 */
public class Constants {

	public static final String COMMAND_LINE_FORMAT = "\n\n*****INFO: COMMAND LINE : java -jar <jar-name>.jar <file-name>.txt or cat <file-name>.txt | java -jar <jar-name>.jar <Optional: Planning date Future>. PRESS CTRL+C to EXIT.*****\n\n";
	public static final String INCORRECT_DATE_FORMAT = "Incorrect Date Format. Please Enter the FUTURE date in dd-MM-yyyy Format.";
	public static final String INCORRECT_PATH = "Incorrect File or Incorrect Path.\n";
	public static final String BOOT_APP_MSG = "---------------------Please Wait. SPRING BOOT is initialising-----------------------";
	public static final String GENERIC_ERROR_MSG = "\nPlease fix the Input Data File and execute again.\n";
	public static final int MAX_WEEKS = 48;
	public static final String QA = "QA";
	public static final String OPS = "Ops";
	public static final String SKILL_SCARCITY = "No Skills : No Available skillsets in the Organization";
	public static final String ZERO_SKILLS = "No Skills :";
	public static final String NOT_FEASIBLE = "  Not Feasible:";
	public static final String NO_TIME = "No Time";
	public static final String PROJECTS = "projects";
	public static final String FILE_PROCESS_FAILURE_MSG = "Please provide the proper File to Process.......";
	public static final String INPUT_FILE_PROCESS_ERROR = "Input File processing Error.";
	public static final String EMPTY_FILE = "Input File can't be closed properly.";
	public static final String ILLEGAL_CHARS = "[~$<>*^!#`\"{}$&+;=?#|]";
	public static final String ILLEGAL_CHARS_MSG = "File containst Special Chars like: ~$<>*^!#`\"{}$&+;=?#|";
	public static final String WRONG_FORMAT_FILE = "\n\n\nInput format is worng. Please validate the Input File.\n\n\n";
	public static final String INVALID_USER_INPUT = "\n\n\nInvalid User Input.\n\n\n";
	public static final String INVALID_USER_INPUT_NO_NEWLINE = "Invalid User Input.";
	public static final String FILE_DELIMITERS = "PEOPLE|PROJECTS";
	public static final String USER_DATA_VALID_REGEX = "((?:[a-zA-Z_0-9\\._@ -,]+))";
	public static final String PROJECT_DATA_VALID_REGEX = USER_DATA_VALID_REGEX + "(\\()" + USER_DATA_VALID_REGEX
			+ "(\\))" + "( ?)" + "[-_]" + "( ?)" + "(\\d+)" + ".*week.*";
	public static final String SEPARATOR_CHARS = "@@";
	public static final String INVALID_PROJECT_INPUT = "\n\n\nInvalid Project Input.\n\n\n";
	public static final String ERROR = "\n ERROR:";
	public static final String DATE_FORMAT_ERROR = "Date Format error in plannning.";
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String PAST_DATE = "Past Date. Please enter future date";
	public static final String FIRST_WEEK = "1st Week of ";
	public static final String SECOND_WEEK = "2nd Week of ";
	public static final String THIRD_WEEK = "3rd Week of ";
	public static final String FOURTH_WEEK = "4th Week of ";
	public static final String DUR_IN_WEEKS = "durationInWeeks";
	public static final String START_DATE = "  Start: ";
	public static final String END_DATE = "  End: ";
	public static final String TEAM_NAMES = "  Team: ";

	public static final String USER_INPUT_VALID_FILE = "PEOPLE Carl:Java Lenny:Java Bart:JavaScript Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA  PROJECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ DAM (3xJava, 2xJavaScript) - 5 weeks@@ XYZ (1xRuby) - 1 weeks@@ ABC (2x.net, 1xJavaScript) - 10 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_ALL_PROJECTS_ASSIGNED = "PEOPLE Carl:Java Lenny:Java Bart:JavaScript Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA  PROJECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ XYZ (1xRuby) - 1 weeks@@ ABC (2x.net, 1xJavaScript) - 10 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_PROJECTS_LONGER = "PEOPLE Carl:Java Lenny:Java Bart:JavaScript Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA  PROJECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ XYZ (2xJava, 2xJavaScript) - 45 weeks@@ DAM (1xRuby) - 1 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_INVALID_FILE_SPECIAL_CHARS = "PEOPLE Carl:Java Lenny:Java Bart:Java~Script Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA  PROJ<ECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ XYZ (2^xJava, 2xJavaScript) - 45 weeks@@ DAM (1xRu%by) - 1 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_INVALID_FILE_INVALID_INPUT2 = "PEOPLE CarlJava Lenny:Java Bart:JavaScript Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA  PROJECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ XYZ (2xJava, 2xJavaScript) - 45 weeks@@ DAM (1xRuby) - 1 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_INVALID_FILE_INVALID_INPUT3 = "PEOPLE CarlJava Lenny:Java Bart:JavaScript Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA  PROJECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ XYZ () - 45 weeks@@ DAM (1xRuby) - 1 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_INVALID_FILE_NO_PEOPLE = "PROJECTS PIM (2x.net, 1xJavaScript) - 35 weeks@@ XYZ (2x.net, 1xJavaScript) - 45 weeks@@ DAM (1xRuby) - 1 weeks@@ DEF (2xRuby) - 21 weeks@@ SEF (2xJava) - 4 weeks@@";
	public static final String USER_INPUT_INVALID_FILE_NO_PROJECTS = "PEOPLE CarlJava Lenny:Java Bart:JavaScript Moe:JavaScript Milhouse:.net Lisa:.net Ned:QA Edna:QA Barney:Ops Homer:Ops Homer1:Ruby Homer2:Ruby Homer3:Ruby bob:QA";

	public static final String PROJECT_NAME_PIM = "PIM";
	public static final String PROJECT_NAME_DAM = "DAM";
	public static final String PROJECT_NAME_XYZ = "XYZ";
	public static final String PROJECT_SKILL_JAVA = "Java";

}