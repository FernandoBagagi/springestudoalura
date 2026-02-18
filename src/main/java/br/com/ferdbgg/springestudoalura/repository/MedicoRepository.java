package br.com.ferdbgg.springestudoalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
