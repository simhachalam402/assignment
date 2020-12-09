# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

Step 1: Download this application from GitHub by using below command are use tortoisegit
		git clone https://github.com/spring-guides/gs-testing-web.git
Step 2: Open project directory (Parallel to "src" directory)
Step 3: Open command prompt
Step 5: Build you application by skipping test cases, because test cases needs application need to be in running state
        $ mvn install -Dmaven.test.skip=true
Step 6: Once build is successfull you will get target directory with .jar file in it. (ex: assignment\target\assignment.jar)
Step 7: You can use either one from below commands to run you application
$ mvn spring-boot:run  (Our application will starts)
or		
$ java -jar target/assignment.jar
		
		
		After that we can execute the following rest services from postman
POST: http://localhost:9999/assignment/api/addPerson
	  Case 1 (JSON): {"firstName":"Simhachalam","lastName":"Pydana"}
	  Case 2 (XML ): <person>
			    <firstName>Johnny</firstName>
			    <lastName>MD</lastName>
			  </person>
PUT: http://localhost:9999/assignment/api/editPerson/{id}
	  {"firstName":"Simhachalam_1","lastName":"Pydana_1"}
	  
DELETE: http://localhost:9999/assignment/api/deletePerson/{id}
	  http://localhost:9999/assignment/api/deletePerson/1
	  
GET: http://localhost:9999/assignment/api/personsCount

GET: http://localhost:9999/assignment/api/persons

GET: http://localhost:9999/assignment/api/person/{id}
	  http://localhost:9999/assignment/api/person/1	
	  
Step 8: Once the application started over port 9999, onen another command prompt (prallel to src directory) and execute below command
		mvn test