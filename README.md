# assignment
 assignment on spring boot application
 
Step 1: Download this application from GitHub by using below command are use tortoisegit git clone https://github.com/spring-guides/gs-testing-web.git Step 2: Open project directory (Parallel to "src" directory) Step 3: Open command prompt Step 5: Build you application by skipping test cases, because test cases needs application need to be in running state $ mvn install -Dmaven.test.skip=true Step 6: Once build is successfull you will get target directory with .jar file in it. (ex: assignment\target\assignment.jar) Step 7: You can use either one from below commands to run you application $ mvn spring-boot:run (Our application will starts) or $ java -jar target/assignment.jar

	After that we can execute the following rest services from postman
POST: http://localhost:9999/assignment/addPerson Case 1 (JSON): {"firstName":"Simhachalam","lastName":"Pydana"} Case 2 (XML ): Johnny MD PUT: http://localhost:9999/assignment/editPerson/{id} {"firstName":"Simhachalam_1","lastName":"Pydana_1"}

DELETE: http://localhost:9999/assignment/deletePerson/{id} http://localhost:9999/assignment/deletePerson/1

GET: http://localhost:9999/assignment/api/personsCount

GET: http://localhost:9999/assignment/api/persons

GET: http://localhost:9999/assignment/api/person/{id} http://localhost:9999/assignment/api/person/1

Step 8: Once the application started over port 9999, onen another command prompt (prallel to src directory) and execute below command mvn test
