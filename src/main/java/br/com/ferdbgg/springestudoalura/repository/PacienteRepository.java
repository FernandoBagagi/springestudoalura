package br.com.ferdbgg.springestudoalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
