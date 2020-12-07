package com.example.assignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

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
		Person p1_save = personRepository.saveAndFlush(p1);
		Person p2_save = personRepository.saveAndFlush(p2);
		
		List<Person> personsList = personRepository.findAll();
		
		assertThat(personsList.size()).isNotEqualTo(0);
		assertTrue(personsList.contains(p1_save));
		assertThat(personsList.get(0)).isEqualTo(p1_save);
		assertThat(personsList.get(1)).isEqualTo(p2_save);
		LOGGER.info("whenFindAll case got finished >> {}", personsList);
	}
	
	@Test
	@Order(2)
	public void whenFindAllById() {
		LOGGER.info("whenFindAllById case got triggered >> ");
		Person p3 = new Person("Test3", "T3");
		Person p4 = new Person("Test4", "T4");
		Person p3_save = personRepository.saveAndFlush(p3);
		personRepository.saveAndFlush(p4);
		
		Optional<Person> testPerson = personRepository.findById(p3_save.getId());
		assertThat(testPerson.get().getId()).isEqualTo(p3.getId());
		assertThat(testPerson.get().getFirstName()).isEqualTo(p3.getFirstName());
		LOGGER.info("whenFindAllById case got finished >> {}", testPerson.get());
	}
	
	@Test
	@Order(3)
	public void whenDeleteById() {
		LOGGER.info("whenDeleteById case got triggered >> ");
		Person p5 = new Person("Test5", "T5");
		Person p6 = new Person("Test6", "T6");
		Person p5_save = personRepository.saveAndFlush(p5);
		personRepository.saveAndFlush(p6);
		
		Optional<Person> testPerson = personRepository.findById(p5_save.getId());
		if(testPerson.isPresent()) {
			LOGGER.info("Fetched person object for deletion process is {}", p5_save);
			personRepository.delete(testPerson.get());
			Person pp = personRepository.findById(p5_save.getId()).orElse(null);
			if(pp==null) {
				assertThat(true);
			} else {
				assertThat(false);
			}
		} else {
			LOGGER.info("Looking for person id {} is not available.", p5_save.getId());
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
		Person p7_save = personRepository.saveAndFlush(p7);
		personRepository.saveAndFlush(p8);
		
		Optional<Person> testPerson = personRepository.findById(p7_save.getId());
		if(testPerson.isPresent()) {
			LOGGER.info("Fetched person object for updation process is available in db. Person Id: ", p7_save.getId());
			Person updateObj = testPerson.get();
			updateObj.setFirstName("Test7-1");
			updateObj.setLastName("T7-1");
			Person p9_save = personRepository.saveAndFlush(updateObj);
			LOGGER.info("Fetched person object is updated successfully. And person object is {}", p9_save);
			assertThat(p9_save!=null);
			
			testPerson = personRepository.findById(p9_save.getId());
			if(testPerson.isPresent()) {
				LOGGER.info("Fetch updated person object is availeble in db. {}", testPerson.get());
				assertThat(testPerson.get().getFirstName()).isEqualTo("Test7-1");
			} else {
				LOGGER.info("Fetch updated person object is not available in db. {}", testPerson.get());
				assertThat(false);
			}
		} else {
			LOGGER.info("Couldn't find the person object which needs to update. PersonId: ",p7_save.getId());
			assertThat(false);
		}
		LOGGER.info("whenUpdateById case got finished >> ");
	}
}
