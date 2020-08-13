package net.elmadigital.tutorsmanager.dao;

import java.util.List;
import net.elmadigital.tutorsmanager.model.Tutor;

public interface TutorsDAO {
	
	public List<Tutor> getAllTutors();

	public Tutor getTutor(long id);
	
	public void addTutor(Tutor tutor);
	
	public Tutor updateTutor(Tutor tutor, long id);

	public void deleteTutor(long id);
	
	public List<Tutor> getTutorsByNameAndCode(String name, String code);
	
}
