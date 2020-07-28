# amazon-demo-tests
This repo contains demo test cases which are written as part of assignment in interview

# prerequisites for running tests
1) Java 9
2) Maven
3) Appium
4) Android setup
5) Emulator or Real device

# Configuration files
pom.xml (If testng.xml path is updated) <br>
src/test/resources/configurations.properties (for modifying login user name and password) <br>
src/test/resources/log4j.xml (for changing logging configurations) <br>
src/test/resources/testng.xml (for configuring test cases that need to be run and any parameters that are used for execution) <br>
src/test/resources/Amazon_shopping.apk (When a new version of app is ready, replace old app with new app) <br>

# How to run test cases?
clone the git repo and run 'mvn clean install' command.
