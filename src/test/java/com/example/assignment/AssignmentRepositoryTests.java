package com.example.assignment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.assignment.model.Person;
import com.example.assignment.repository.PersonRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class AssignmentRepositoryTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentRepositoryTests.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Test
	@Order(1)
	public void whenFindAll() {
		LOGGER.info("whenFindAll case got triggered >> ");
		Person p1 = new Person("Test1", "T1");
		Person p2 = new Person("Test2", "T2");
		personRepository.saveAndFlush(p1);
		personRepository.saveAndFlush(p2);
		
		List<Person> personsList = personRepository.findAll();
		
		assertThat(personsList.size()).isEqualTo(2);
		assertThat(personsList.get(0)).isEqualTo(p1);
		assertThat(personsList.get(1)).isEqualTo(p2);
		LOGGER.info("whenFindAll case got finished >> ");
	}
	
	@Test
	@Order(2)
	public void whenFindAllById() {
		LOGGER.info("whenFindAllById case got triggered >> ");
		Person p3 = new Person("Test3", "T3");
		Person p4 = new Person("Test4", "T4");
		Person p3_book = personRepository.saveAndFlush(p3);
		personRepository.saveAndFlush(p4);
		
		Optional<Person> testPerson = personRepository.findById(p3_book.getId());
		
		assertThat(testPerson.get().getFirstName()).isEqualTo(p3.getFirstName());
		LOGGER.info("whenFindAllById case got finished >> ");
	}
	
	@Test
	@Order(3)
	public void whenDeleteById() {
		LOGGER.info("whenDeleteById case got triggered >> ");
		Person p5 = new Person("Test5", "T5");
		Person p6 = new Person("Test6", "T6");
		Person p5_book = personRepository.saveAndFlush(p5);
		personRepository.saveAndFlush(p6);
		
		Optional<Person> testPerson = personRepository.findById(p5_book.getId());
		if(testPerson.isPresent()) {
			personRepository.delete(testPerson.get());
			Person pp = personRepository.findById(p5_book.getId()).orElse(null);
			if(pp==null) {
				assertThat(true);
			} else {
				assertThat(false);
			}
		} else {
			assertThat(false);
		}
		LOGGER.info("whenDeleteById case got finished >> ");
	}
	
	@Test
	@Order(4)
	public void whenUpdateById() {
		LOGGER.info("whenUpdateById case got triggered >> ");
		Person p7 = new Person("Test7", "T7");
		Person p8 = new Person("Test8", "T8");
		Person p7_book = personRepository.saveAndFlush(p7);
		personRepository.saveAndFlush(p8);
		
		Optional<Person> testPerson = personRepository.findById(p7_book.getId());
		if(testPerson.isPresent()) {
			Person updateObj = testPerson.get();
			updateObj.setFirstName("Test7-1");
			updateObj.setLastName("T7-1");
			Person p9_book = personRepository.saveAndFlush(updateObj);
			testPerson = personRepository.findById(p9_book.getId());
			if(testPerson.isPresent()) {
				assertThat(testPerson.get().getFirstName()).isEqualTo("Test7-1");
			} else {
				assertThat(false);
			}
		} else {
			assertThat(false);
		}
		LOGGER.info("whenUpdateById case got finished >> ");
	}
}
