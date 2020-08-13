package net.elmadigital.tutorsmanager.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		// TODO Auto-generated method stub
	}

	@Override
	public Tutor updateTutor(Tutor tutor, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTutor(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Tutor> getTutorsByNameAndCode(String name, String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
