package com.example.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.Person;
import com.example.assignment.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	PersonRepository personRepository;

	@Override
	public Person addPerson(Person person) {
		LOGGER.info("Saving person object process begins >> {}", person);
		return personRepository.save(new Person(person.getFirstName(), person.getLastName()));
	}

	@Override
	public Person editPerson(long id, Person person) {
		LOGGER.info("Editing person object process begins >> Id: {}, Person: {}", id, person);
		Optional<Person> personData = personRepository.findById(id);

		if (personData.isPresent()) {
			Person p1 = personData.get();
			p1.setFirstName(person.getFirstName());
			p1.setLastName(person.getLastName());
			return personRepository.save(p1);
		} else {
			return null;
		}
	}

	@Override
	public boolean deletePerson(long id) {
		LOGGER.info("Deletion person process begins >> Person Id: {}", id);
		Optional<Person> personData = personRepository.findById(id);
		if (personData.isPresent()) {
			personRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Person> getPersonsList() {
		LOGGER.info("Fetching list of person's process begins >> ");
		List<Person> persons = new ArrayList<Person>();
		personRepository.findAll().forEach(persons::add);
		return persons;
	}

	@Override
	public Person getPersonById(long id) {
		LOGGER.info("Fetching person by Idprocess begins >> ");
		Optional<Person> person = personRepository.findById(id);
		return person.get();
	}

}
