package net.elmadigital.tutorsmanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import net.elmadigital.tutorsmanager.model.Tutor;

public interface TutorsRepository extends JpaRepository<Tutor, Long> {
	
	public Optional<Tutor> findByTutCode(String tutCode);
	
	List<Tutor> findByNameAndPostcode(String name, String code);

}
