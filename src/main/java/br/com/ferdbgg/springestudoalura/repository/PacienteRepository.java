package br.com.ferdbgg.springestudoalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
