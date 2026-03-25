package br.com.ferdbgg.springestudoalura.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p.id FROM Paciente p WHERE p.id = :id AND p.ativo = TRUE")
    Paciente getReferenceByIdAndAtivoTrue(Long id);

    <T> Page<T> findByAtivo(Boolean ativo, Class<T> type, Pageable pageable);

    <T> Optional<T> findByIdAndAtivo(Long id, Boolean ativo, Class<T> type);

}
