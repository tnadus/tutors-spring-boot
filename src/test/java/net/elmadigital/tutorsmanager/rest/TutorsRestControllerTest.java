package net.elmadigital.tutorsmanager.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import net.elmadigital.tutorsmanager.dao.TutorsDAO;
import net.elmadigital.tutorsmanager.exception.TutorNotFoundException;
import net.elmadigital.tutorsmanager.model.Tutor;
import net.elmadigital.tutorsmanager.service.TutorsService;

@RunWith(SpringRunner.class)
@WebMvcTest(TutorsRestController.class)
public class TutorsRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TutorsService tutorsService;
	
	protected String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	protected <T> T jsonToObject(String jsonString, Class<T> classType) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonString, classType);
	}
	
	@Test
	public void getAllTutorsShouldReturnTutors() throws Exception {
		
		when(tutorsService.getAllTutors()).thenReturn(TutorsDAO.TUTORS);
		
		mockMvc.perform(get("/tutors"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(4)))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", is("John")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[1].email", is("victorpalmer@yahoo.com")));
		
		verify(tutorsService, times(1)).getAllTutors();		
	}
	
	@Test
	public void getTutorReturnsATutor() throws Exception {
		
		when(tutorsService.getTutor(anyLong())).thenReturn(TutorsDAO.TUTORS.get(0));
		
		mockMvc.perform(get("/tutors/0"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(0)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.postcode", is("AB12CD")));

		verify(tutorsService, times(1)).getTutor(0);
	}
	
	@Test
	public void getTutorReturnsHttpNotFoundWhenNoTutorExists() throws Exception {
		
		when(tutorsService.getTutor(anyLong())).thenThrow(TutorNotFoundException.class);
		
		mockMvc.perform(get("/tutors/0"))
		.andDo(print())
		.andExpect(status().isNotFound());
		
		verify(tutorsService, times(1)).getTutor(0);
	}
	
	@Test
	public void postNonExistedValidTutorReturnsHttpCreated() throws Exception {
		
		Tutor tutor = new Tutor(4, "mock_name", 
				"mock_surname", 
				"mock_email@gmail.com", 
				"MM00CK", 
				new String[]{"Mock101", "Mock102"}, 
				"TUT-123");
		
		String tutorJson = mapToJson(tutor);
		
		mockMvc.perform(post("/tutors")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(tutorJson))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(4)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", is("mock_name")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.email", is("mock_email@gmail.com")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.expertizeAreas[0]", is("Mock101")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.expertizeAreas[1]", is("Mock102")));
		
		verify(tutorsService, times(1)).addTutor(any());
	}
	
	@Test
	public void postNonExistedEmptyNameTutorThrowsMethodArgumentNotValidException() throws Exception {
		
		Tutor tutor = new Tutor(4, "", 
				"mock_surname", 
				"mock_email@gmail.com", 
				"MM00CK", 
				new String[]{"Mock101", "Mock102"}, 
				"TUT-123");
		
		String tutorJson = mapToJson(tutor);
		mockMvc.perform(post("/tutors")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(tutorJson))
		.andDo(print())
		.andExpect(status().isBadRequest());
		
		verify(tutorsService, times(0)).addTutor(any());
		
	}
	
	@Test
	public void postNonExistedInvalidPostcodeTutorThrowsMethodArgumentNotValidException() throws Exception {
		
		Tutor tutor = new Tutor(4, "", 
				"mock_surname", 
				"mock_email@gmail.com", 
				"ABC00", 
				new String[]{"Mock101", "Mock102"}, 
				"TUT-123");
		
		String tutorJson = mapToJson(tutor);
		mockMvc.perform(post("/tutors")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(tutorJson))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", is("BAD_REQUEST")));
				
		verify(tutorsService, times(0)).addTutor(any());
	}
	
}