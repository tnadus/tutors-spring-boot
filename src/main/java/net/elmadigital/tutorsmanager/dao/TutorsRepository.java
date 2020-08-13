package net.elmadigital.tutorsmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.elmadigital.tutorsmanager.model.Tutor;

public interface TutorsRepository extends JpaRepository<Tutor, Long> {

}
