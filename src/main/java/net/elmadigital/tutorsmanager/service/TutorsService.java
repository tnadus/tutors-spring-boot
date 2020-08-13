package net.elmadigital.tutorsmanager.service;

import java.util.List;

import net.elmadigital.tutorsmanager.model.Tutor;

public interface TutorsService {
	
	public List<Tutor> getAllTutors();
	
	public Tutor getTutor(long id);
	
	public void addTutor(Tutor tutor);
	
	public Tutor updateTutor(Tutor tutor, long id);
	
	public void deleteTutor(long id);
	
	public List<Tutor> getTutorsByNameAndCode(String name, String code);
}
