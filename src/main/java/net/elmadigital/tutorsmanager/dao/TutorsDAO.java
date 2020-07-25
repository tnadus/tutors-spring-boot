package net.elmadigital.tutorsmanager.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.descriptor.tld.TldRuleSet.Variable;
import org.springframework.stereotype.Repository;

import net.elmadigital.tutorsmanager.exception.TutorAlreadyExistedException;
import net.elmadigital.tutorsmanager.exception.TutorNotFoundException;
import net.elmadigital.tutorsmanager.model.Tutor;

@Repository
public class TutorsDAO {

	private List<Tutor> tutors = new ArrayList<>(Arrays.asList(
			new Tutor(0, "John", "Dome", "john_dome@gmail.com", new String[]{"iOS", "Android"}),
			new Tutor(1, "Victor", "Toll", "victor_toll@yahoo.com", new String[]{"Java", "Spring"}),
			new Tutor(2, "Sally", "Simson", "sally_sims@yahoo.com", new String[]{"Word", "Excel"})));
	
	public List<Tutor> getAllTutors() {
		return tutors;
	}

	public Tutor getTutor(long id) {
		return tutors.stream().filter(tut -> tut.getId() == id).findFirst().orElseThrow(() -> new TutorNotFoundException());
	}

	public void addTutor(Tutor tutor) {
		tutors.stream()
		.filter(tut -> tut.getId() == tutor.getId())
		.findAny()
		.ifPresent(nonEmpty -> { 
			throw new TutorAlreadyExistedException();
		});
		tutors.add(tutor);
	}

	public Tutor updateTutor(Tutor tutor, long id) {
		Tutor tutorFound = getTutor(id);
		tutorFound.setName(tutor.getName());
		tutorFound.setSurname(tutor.getSurname());
		tutorFound.setEmail(tutor.getEmail());
		tutorFound.setExpertizeAreas(tutor.getExpertizeAreas());
		return tutorFound;
	}

	public void deleteTutor(long id) {
		Tutor tutor = getTutor(id);
		tutors.remove(tutor);
	}
			
}
