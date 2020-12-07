package com.example.assignment.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.assignment.model.Person;

public interface PersonService {

	public Person addPerson(Person person);
	public Person editPerson(long id, Person person);
	public boolean deletePerson(long id);
	public List<Person> getPersonsList();
	public Person getPersonById(long id);
	
}
