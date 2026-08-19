package br.com.ferdbgg.springestudoalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.model.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    <T> Page<T> findByUsuarioAtivo(Boolean ativo, Class<T> type, Pageable pageable);

    <T> Optional<T> findByIdAndUsuarioAtivo(Long id, Boolean ativo, Class<T> type);

    <T> List<T> findAll(Class<T> type);

}
