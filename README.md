# Introduction
This application is a student project created by Emma Walsh and assisted by Ash-Lee Hommy.
It is a Java Spring Boot REST API application, built and run by Maven. 
It provides a simple platform for runners to log their running information and runs.

# Build and Test
To be able to run this project, you must first install the Java Development Kit, Maven, and
PostgreSQL. The IDE that I use is IntelliJ IDEA Community Edition. 
- JDK: a package of tools for developing Java-based software
- Maven: a build automation tool used primarily for Java projects
- PostgreSQL: a database management system

 Using the command "mvn clean install" will handle building and testing the
project. The "clean" command clears the target directory.
The "install" command compiles, tests and packages the project
and installs the resulting artifact into your local Maven repository.
Using the command "mvn spring-boot:run" in the project terminal will run
the project. If you would like to manually test the project's endpoints,
you can visit the [Swagger endpoint][0].
# Getting Started
To use this application, you must simply visit [the login page][1].
From there, you can choose to either sign into a previously created account, or create a new one.
Once you are logged into your account, you can try logging a run. 

To read more about the running-log API, visit the [running-log wiki][2]

[0]:http://localhost:8080/swagger-ui.html
[1]:http://localhost:8080/
[2]:https://github.com/emawlsh/running-log--api/wiki
