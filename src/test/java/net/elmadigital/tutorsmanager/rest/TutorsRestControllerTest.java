package net.elmadigital.tutorsmanager.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;

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
	
}
