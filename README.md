# Introduction
This application is a student project created by Emma Walsh and assisted by Ash-Lee Hommy.
It is a Spring Boot REST API application that allows a runner to log their runs.

# Build and Test
Using the command "mvn clean install" will handle building and testing the
project. The "clean" command clears the target directory.
The "install" command compiles, tests and packages the project
and installs the resulting artifact into your local Maven repository.
Using the command "mvn spring-boot:run" in the project terminal will run
the project. If you would like to manually test the project's endpoints,
you can visit http://localhost:8080/swagger-ui.html
# Getting Started
To use this application, you must simply visit http://localhost:8080/.
From there, you can choose to either sign into a previously created account, or create a new one.
Once you are logged into your account, you can try logging a run. A run can include the following information:
- Date
- Total time
- Total distance
- Average speed
- Weather conditions

You can also edit your account information.
A runner's account contains the following information:
- Name
- Email
- General location
  (if you would like to be able to include weather conditions when logging runs)
- logged runs
