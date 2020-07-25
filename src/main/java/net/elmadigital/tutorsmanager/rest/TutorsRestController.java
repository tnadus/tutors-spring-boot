package net.elmadigital.tutorsmanager.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.elmadigital.tutorsmanager.model.Tutor;
import net.elmadigital.tutorsmanager.service.TutorsService;

@RestController
public class TutorsRestController {
	
	@Autowired
	private TutorsService tutorsService;
	
	@GetMapping("/tutors")
	@ResponseStatus(HttpStatus.OK)
	public List<Tutor> getAllTutors() {
		return tutorsService.getAllTutors();
	}
	
	@GetMapping("/tutors/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Tutor getTutor(@PathVariable long id) {
		return tutorsService.getTutor(id);		
	}
	
	@PostMapping("/tutors")
	@ResponseStatus(HttpStatus.CREATED)
	public Tutor addTutor(@RequestBody Tutor tutor) {
		tutorsService.addTutor(tutor);
		return tutor;
	}
	
	@PutMapping("/tutors/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Tutor updateTutor(@RequestBody Tutor tutor, @PathVariable long id) {
		return tutorsService.updateTutor(tutor, id);
		
	}
	
	@DeleteMapping("/tutors/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteTutor(@PathVariable long id) {
		tutorsService.deleteTutor(id);
	}
}
