package com.example.assignment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.model.Person;
import com.example.assignment.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	PersonService personService;

	@PostMapping(path = "/addPerson", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		LOGGER.info("Add person service got triggered >> {}", person);
		try {
			Person p1 = personService.addPerson(person);
			LOGGER.info("Creation of an new person is successfull >> {}", person);
			return new ResponseEntity<>(p1, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error("Add person service got exception >> {}", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/editPerson/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Person> editPerson(@PathVariable("id") long id, @RequestBody Person person) {
		LOGGER.info("Edit person service got triggered >> update for person {} with {}", id, person);
		Person p1 = personService.editPerson(id, person);
		if (p1 != null) {
			LOGGER.info("Updation of an existing person is successfull >> Person: {}", p1);
			return new ResponseEntity<>(p1, HttpStatus.OK);
		} else {
			LOGGER.error("Person id {} is not exists.", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deletePerson/{id}")
	public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") long id) {
		LOGGER.info("Delete person service got triggered >> for person id {}", id);
		boolean deleteStatus = personService.deletePerson(id);
		try {
			if (deleteStatus) {
				LOGGER.info("Deletion of an existing person is successfull >> Person Id: {}", id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				LOGGER.error("Person id {} is not exists.", id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("Delete person service got exception >> {}", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/personsCount")
	public ResponseEntity<String> getPersonsCount() {
		LOGGER.info("Count of persons service got triggered >> ");
		try {
			List<Person> persons = personService.getPersonsList();
			if (persons.isEmpty()) {
				LOGGER.info("Count of persons service returns empty >> ");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			LOGGER.info("Availble persons count is {}", persons.size());
			return new ResponseEntity<>("Availble persons count is " + persons.size(), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Count of persons service got exception >> {}", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getPersonsList() {
		LOGGER.info("Get list of persons service got triggered >> ");
		try {
			List<Person> persons = personService.getPersonsList();
			if (persons.isEmpty()) {
				LOGGER.info("Get list of persons service returns empty >> ");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			LOGGER.info("list of persons available >> {}", persons.size());
			return new ResponseEntity<>(persons, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Get list of persons service got exception >> {}", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/person/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable("id") long id) {
		LOGGER.info("Get person by Id service got triggered >> ");
		try {
			Person person = personService.getPersonById(id);
			if(person!=null) {
				LOGGER.info("Fetched person details are >> {}", person);
				return new ResponseEntity<>(person, HttpStatus.OK);
			} else {
				LOGGER.info("No person is available with Id: {} >> ", id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("Get person by Id service got exception >> {}", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
