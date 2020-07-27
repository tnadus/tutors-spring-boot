package net.elmadigital.tutorsmanager.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import net.elmadigital.tutorsmanager.exception.TutorAlreadyExistedException;
import net.elmadigital.tutorsmanager.exception.TutorNotFoundException;
import net.elmadigital.tutorsmanager.model.Tutor;

@Repository
public class TutorsDAO {

	private List<Tutor> tutors = new ArrayList<>(Arrays.asList(
			new Tutor(0, "John", "Cooper", "john_cooper@gmail.com", new String[]{"iOS", "Android", "Java"}),
			new Tutor(1, "Victor", "Palmer", "victorpalmer@yahoo.com", new String[]{"Java", "Spring"}),
			new Tutor(2, "Sally", "Simson", "sally_sims@yahoo.com", new String[]{"Word", "Excel"}),
			new Tutor(3, "Ali", "Tatar", "alitatar@gmail.com", new String[]{"Oracle", "Java"})));
	
	public List<Tutor> getAllTutors() {
		return tutors;
	}

	public Tutor getTutor(long id) {
		return tutors.stream()
				.filter(tut -> tut.getId() == id)
				.findFirst()
				.orElseThrow(TutorNotFoundException::new);
	}
	
	public void addTutor(Tutor tutor) {
		tutors.stream()
		.filter(tut -> tut.getId() == tutor.getId())
		.reduce((tutor1, tutor2) -> {
			//accumulation function is called to operate if has more than 1 element
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

	public List<Tutor> getTutorsByNameAndSurname(String name, String surname) {		
		return Optional.of(tutors.stream()
                .filter(tut -> (tut.getName().equalsIgnoreCase(name)) && (tut.getSurname().equalsIgnoreCase(surname)))
                .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(TutorNotFoundException::new);
	}
}