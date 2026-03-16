package br.com.ferdbgg.springestudoalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p.id FROM Paciente p WHERE p.id = :id AND p.ativo = TRUE")
    Paciente getReferenceByIdAndAtivoTrue(Long id);

}
