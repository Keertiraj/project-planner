### Application - Project Planner

#### Technologies used in the project

* Java 7
* Spring Data JPA (for persistence)
* Spring Boot

#### Getting started

To run the application, follow these steps:

1. Build the project via Maven:

    <code>$ mvn clean install</code>

2. Start the application:

   From command line execute the one of the following commands:

    <code>$ cat <file-name>.txt | java -jar target/project-planner-0.0.1-SNAPSHOT.jar </code>
    
    OR
    
    <code>$ java -jar target/project-planner-0.0.1-SNAPSHOT.jar <file-name>.txt </code>
    
   By Default, the application estimates the planning from the beginning of the year. To run the application for specific **Future date (dd-MM-yyyy)**, use following command:
   
    <code>$ java -jar target/project-planner-0.0.1-SNAPSHOT.jar < file-name.txt > < Future-Date(dd-MM-yyyy ></code>
 
#### Rules followed for the Project

1. Runs as command line application. File can be passed as an input parameter or from STDIN. 
2. Packaged as self running spring boot application. 
3. README.md file is created.
4. Following frameworks are used:
    1. Spring Boot - This can be deployed as self contained jar. Spring Boot helps in injecting in memory database easily. 
    2. JUnit - For Integration testing
5. Any person having Java 7 and Maven 3 installed can run the application
6. Following design points are considered for this application:
    1. Re-usability: EMPLOYEE, SKILL, PROJECT Entities are created for this application to store the Input data. All these entities have many to many relationships(EmployeeProject, EmployeeSkill, ProjectSkill).
       We can easily integrate this application with WEB layer. We just need expose the REST Interfaces.
    2. Performance: The assignment of Projects to employees has the linear time complexity of O(n).
    3. Project Planner: Project planner runs for the beginning of the year. Application can also be run for specific future date by passing the date parameter.
7. JUnit integrations are written for various scenarios to make sure that the application changes do not break the functionality. 
