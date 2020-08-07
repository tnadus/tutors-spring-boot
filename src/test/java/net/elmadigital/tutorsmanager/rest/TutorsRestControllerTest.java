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


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.naming.NameNotFoundException;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
	    .andExpect(MockMvcResultMatchers.jsonPath("$.[1].postcode", is("WW99AA")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[2].tutCode", is("TUT-591")));
		
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
		.andExpect(MockMvcResultMatchers.jsonPath("$.expertizeAreas[0]", is("Mock101")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.expertizeAreas[1]", is("Mock102")));
		
		verify(tutorsService, times(1)).addTutor(any());
	}
	
	@Test
	public void postNonExistedEmptyNameTutorThrowsMethodArgumentNotValidException() throws Exception {
		
		Tutor tutor = new Tutor(4, "", 
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
	
	@Test
	public void postNonExistedInvalidTutcodeTutorThrowsMethodArgumentNotValidException() throws Exception {
		
		Tutor tutor = new Tutor(4, "", 
				"MM00CK", 
				new String[]{"Mock101", "Mock102"}, 
				"ABC-123");
		
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
	
	@Test
	public void putAValidTutorReturnsOK() throws Exception {
		
		Tutor tutor = new Tutor(0, "updated john", 
				"AB12CD", 
				new String[]{"Mock101", "Mock102"}, 
				"TUT-123");
		
		when(tutorsService.updateTutor(any(), anyLong())).thenReturn(tutor);
		
		String tutorJson = mapToJson(tutor);
		
		mockMvc.perform(put("/tutors/" + tutor.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(tutorJson))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(0)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", is("updated john")));
		
		verify(tutorsService, times(1)).updateTutor(any(), anyLong());
	
	}
	
	@Test
	public void putNonExistedTutorReturnsNotFound() throws Exception {
		
		Tutor tutor = new Tutor(0, "updated john", 
				"AB12CD", 
				new String[]{"Mock101", "Mock102"}, 
				"TUT-123");
		
		when(tutorsService.updateTutor(any(), anyLong())).thenThrow(TutorNotFoundException.class);
		
		String tutorJson = mapToJson(tutor);
		
		mockMvc.perform(put("/tutors/" + tutor.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(tutorJson))
		.andDo(print())
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", is("NOT_FOUND")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Tutor not found, sorry!")));
		
		verify(tutorsService, times(1)).updateTutor(any(), anyLong());
	}
	
	@Test
	public void deleteValidTutorReturnsOK() throws Exception {
		
		mockMvc.perform(delete("/tutors/0"))
		.andDo(print())
		.andExpect(status().isOk());
				
		verify(tutorsService, times(1)).deleteTutor(anyLong());
	}
	
	@Test
	public void deleteNonExistedTutorReturnsNotFound() throws Exception {
		
		doThrow(TutorNotFoundException.class).when(tutorsService).deleteTutor(anyLong());
		
		mockMvc.perform(delete("/tutors/0"))
		.andDo(print())
		.andExpect(status().isNotFound());
				
		verify(tutorsService, times(1)).deleteTutor(anyLong());
	}
}
