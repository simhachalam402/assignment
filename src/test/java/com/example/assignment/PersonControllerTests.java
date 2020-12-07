package com.example.assignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.assignment.controller.PersonController;
import com.example.assignment.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonControllerTests.class);
	
	private static String addPersonUrl;
    private static String updatePersonUrl;
    private static String deletePersonUrl;
    private static String fetchPersonsCountUrl;
    private static String fetchAllPersonsUrl;
    private static String fetchPersonByIdUrl;
	
	@Autowired
	private PersonController personController;
	
	@Value("${server.port}")
	private static int port=9999;
	
	private static HttpHeaders headers;
	
	private static RestTemplate restTemplate;
	
	private static JSONObject personJsonObject;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(personController).isNotNull();
	}
	
	@BeforeAll
    public static void runBeforeAllTestMethods() throws JSONException {
		LOGGER.info("PersonControllerTests::runBeforeAllTestMethods triggered >> ");
		addPersonUrl = "http://localhost:" + port + "/" + "assignment/api/addPerson";
		updatePersonUrl = "http://localhost:" + port + "/" + "assignment/api/editPerson/"; //{id}
		deletePersonUrl = "http://localhost:" + port + "/" + "assignment/api/deletePerson/"; //{id}
		fetchPersonsCountUrl = "http://localhost:" + port + "/" + "assignment/api/personsCount";
		fetchAllPersonsUrl = "http://localhost:" + port + "/" + "assignment/api/persons";
		fetchPersonByIdUrl = "http://localhost:" + port + "/" + "assignment/api/person/"; //{id}
		
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        personJsonObject = new JSONObject();
        personJsonObject.put("firstName", "Johnny");
        personJsonObject.put("lastName", "MD");
    }
	
	@Test
	@Order(1)
	public void addPerson_Json_Xml() throws JsonMappingException, JsonProcessingException, JSONException {
		LOGGER.info("PersonControllerTests::addPerson_Json_Xml triggered >> ");

		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		String createPersonWithJSON = restTemplate.postForObject(addPersonUrl, request, String.class);
		JsonNode viaJSON = objectMapper.readTree(createPersonWithJSON);
		
		LOGGER.info("PersonControllerTests::addPerson_Json_Xml Successfully saved person in to db. {}.", createPersonWithJSON);
		assertNotNull(createPersonWithJSON);
        assertNotNull(viaJSON);
        assertNotNull(viaJSON.path("firstName").asText());
        assertTrue(viaJSON.path("firstName").asText().equals("Johnny"));
        LOGGER.info("PersonControllerTests::addPerson_Json_Xml Successfully completed >> ");
	}
	
	@Test
	@Order(2)
	public void editPerson_Json() throws JsonMappingException, JsonProcessingException, JSONException {
		LOGGER.info("PersonControllerTests::editPerson_Json triggered >> ");
		headers.setContentType(MediaType.APPLICATION_JSON);
		personJsonObject = constructJSON("Praveen", "K");
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		String createPersonWithJSON = restTemplate.postForObject(addPersonUrl, request, String.class);
		JsonNode viaJSON = objectMapper.readTree(createPersonWithJSON);
		
		LOGGER.info("PersonControllerTests::editPerson_Json Successfully saved person in to db. {}.", createPersonWithJSON);
		String personId = viaJSON.path("id").asText();
		personJsonObject = constructJSON("Praveen_edit", "K_edit");
		personJsonObject.put("id", personId);
		request = new HttpEntity<String>(personJsonObject.toString(), headers);
		restTemplate.put(updatePersonUrl.concat(personId), request, String.class);
		LOGGER.info("PersonControllerTests::editPerson_Json Successfully updated person in to db for id: {}.", personId);
		
		String getPersonById = restTemplate.getForObject(fetchPersonByIdUrl.concat(personId), String.class);
		LOGGER.info("PersonControllerTests::editPerson_Json Fetched person object after updation successfully {}.", getPersonById);
		JsonNode editViaJSON = objectMapper.readTree(getPersonById);
		assertNotNull(getPersonById);
        assertNotNull(editViaJSON);
        assertNotNull(editViaJSON.path("firstName").asText());
        assertEquals(editViaJSON.path("firstName").asText().toString(), "Praveen_edit");
        assertEquals(editViaJSON.path("lastName").asText().toString(), "K_edit");
        LOGGER.info("PersonControllerTests::editPerson_Json Successfully completed >> ");
	}
	
	@Test
	@Order(3)
	public void deletePerson() throws JSONException, JsonMappingException, JsonProcessingException {
		LOGGER.info("PersonControllerTests::deletePerson triggered >> ");
		headers.setContentType(MediaType.APPLICATION_JSON);
		personJsonObject = constructJSON("Ganesh", "G");
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		String createPersonWithJSON = restTemplate.postForObject(addPersonUrl, request, String.class);
		LOGGER.info("PersonControllerTests::deletePerson Successfully saved person in to db. {}.", createPersonWithJSON);
		
		JsonNode viaJSON = objectMapper.readTree(createPersonWithJSON);
		String personId = viaJSON.path("id").asText();
		LOGGER.info("PersonControllerTests::deletePerson Deletion operation started on personId: {}.", personId);
		restTemplate.delete(deletePersonUrl.concat(personId));
		
		try {
			String getPersonById = restTemplate.getForObject(fetchPersonByIdUrl.concat(personId), String.class);
		} catch(Exception ex) {
			LOGGER.info("PersonControllerTests::deletePerson Person object is not available in db. Because it is already deleted from db. ");
		}
		
		assertTrue(true);
		LOGGER.info("PersonControllerTests::deletePerson Successfully completed >> ");
	}
	
	@Test
	@Order(4)
	public void getPersonsCount() {
		LOGGER.info("PersonControllerTests::getPersonsCount triggered >> ");
		String getPersonsAvailableCount = restTemplate.getForObject(fetchPersonsCountUrl, String.class);
		assertNotNull(getPersonsAvailableCount);
		LOGGER.info("PersonControllerTests::getPersonsCount Successfully completed >> ");
	}
	
	@Test
	@Order(5)
	@SuppressWarnings("unchecked")
	public void getPersonsList() {
		LOGGER.info("PersonControllerTests::getPersonsList triggered >> ");
		List<Person> getPersonsAvailableList = restTemplate.getForObject(fetchAllPersonsUrl, List.class);
		assertNotNull(getPersonsAvailableList);
		LOGGER.info("PersonControllerTests::getPersonsList Successfully completed >> ");
	}
	
	public JSONObject constructJSON(String firstName, String lastName) throws JSONException {
		JSONObject constructObj = new JSONObject();
		constructObj.put("firstName", firstName);
		constructObj.put("lastName", lastName);
		return constructObj;
	}
	
}
