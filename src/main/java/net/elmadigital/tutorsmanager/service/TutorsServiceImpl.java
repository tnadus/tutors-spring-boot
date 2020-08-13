package net.elmadigital.tutorsmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.elmadigital.tutorsmanager.dao.TutorsDAO;
import net.elmadigital.tutorsmanager.model.Tutor;

@Service
public class TutorsServiceImpl implements TutorsService {
	
	@Autowired
	@Qualifier(value = "daoH2")
	private TutorsDAO tutorDAO;
	
	public List<Tutor> getAllTutors() {
		return tutorDAO.getAllTutors();
	}

	public Tutor getTutor(long id) {
		return tutorDAO.getTutor(id);
	}

	public void addTutor(Tutor tutor) {
		tutorDAO.addTutor(tutor);
	}

	public Tutor updateTutor(Tutor tutor, long id) {
		return tutorDAO.updateTutor(tutor, id);
	}

	public void deleteTutor(long id) {
		tutorDAO.deleteTutor(id);		
	}

	public List<Tutor> getTutorsByNameAndCode(String name, String code) {
		return tutorDAO.getTutorsByNameAndCode(name, code);
	}
}
