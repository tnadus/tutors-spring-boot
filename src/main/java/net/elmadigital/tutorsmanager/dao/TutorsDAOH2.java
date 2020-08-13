package net.elmadigital.tutorsmanager.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.elmadigital.tutorsmanager.exception.TutorAlreadyExistedException;
import net.elmadigital.tutorsmanager.exception.TutorNotFoundException;
import net.elmadigital.tutorsmanager.model.Tutor;

@Repository(value = "daoH2")
public class TutorsDAOH2 implements TutorsDAO {

	@Autowired
	TutorsRepository tutorsRepository;

	@Override
	public List<Tutor> getAllTutors() {
		return tutorsRepository.findAll();
	}

	@Override
	public Tutor getTutor(long id) {
		return tutorsRepository.findById(id).orElseThrow(TutorNotFoundException::new);
	}

	@Override
	public void addTutor(Tutor tutor) {
		if (tutorsRepository.findByTutCode(tutor.getTutCode()).isPresent()) {
			throw new TutorAlreadyExistedException();
		}
		tutorsRepository.save(tutor);
	}

	@Override
	public Tutor updateTutor(Tutor tutor, long id) {
		Tutor foundTutor = getTutor(id);
		foundTutor.setName(tutor.getName());
		foundTutor.setPostcode(tutor.getPostcode());
		foundTutor.setTutCode(tutor.getTutCode());
		tutorsRepository.save(foundTutor);
		return foundTutor;
	}

	@Override
	public void deleteTutor(long id) {
		Tutor foundTutor = getTutor(id);
		tutorsRepository.delete(foundTutor);
	}

	@Override
	public List<Tutor> getTutorsByNameAndCode(String name, String code) {
		return tutorsRepository.findByNameAndPostcode(name, code);
	}

}
