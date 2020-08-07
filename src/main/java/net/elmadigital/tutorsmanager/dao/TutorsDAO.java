package net.elmadigital.tutorsmanager.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import net.elmadigital.tutorsmanager.exception.TutorAlreadyExistedException;
import net.elmadigital.tutorsmanager.exception.TutorNotFoundException;
import net.elmadigital.tutorsmanager.model.Tutor;

@Repository
public class TutorsDAO {

	public static final List<Tutor> TUTORS = new ArrayList<>(Arrays.asList(
			new Tutor(0, "John", "AB12CD", new String[]{"iOS", "Android", "Java"}, "TUT-001"),
			new Tutor(1, "Victor", "WW99AA", new String[]{"Java", "Spring"}, "TUT-211"), 
			new Tutor(2, "Sally", "XX55YY", new String[]{"Word", "Excel"}, "TUT-591"),
			new Tutor(3, "Ali", "OO11PP", new String[]{"Oracle", "Java"}, "TUT-999")));
	
	public List<Tutor> getAllTutors() {
		return TUTORS;
	}

	public Tutor getTutor(long id) {
		return TUTORS.stream()
				.filter(tut -> tut.getId() == id)
				.findFirst()
				.orElseThrow(TutorNotFoundException::new);
	}
	
	public void addTutor(Tutor tutor) {
		TUTORS.stream()
		.filter(tut -> tut.getId() == tutor.getId())
		.reduce((tutor1, tutor2) -> {
			//accumulation function is called to operate if has more than 1 element
			throw new TutorAlreadyExistedException();
		});
		TUTORS.add(tutor);
	}
	
	public Tutor updateTutor(Tutor tutor, long id) {
		Tutor tutorFound = getTutor(id);
		tutorFound.setName(tutor.getName());
		tutorFound.setExpertizeAreas(tutor.getExpertizeAreas());
		return tutorFound;
	}

	public void deleteTutor(long id) {
		Tutor tutor = getTutor(id);
		TUTORS.remove(tutor);
	}

	public List<Tutor> getTutorsByNameAndCode(String name, String code) {		
		return Optional.of(TUTORS.stream()
                .filter(tut -> (tut.getName().equalsIgnoreCase(name)) && (tut.getTutCode().equalsIgnoreCase(code)))
                .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(TutorNotFoundException::new);
	}
}
