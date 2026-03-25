package br.com.ferdbgg.springestudoalura.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ferdbgg.springestudoalura.domain.entity.Consulta;

public interface ConsultaRepository
        extends JpaRepository<Consulta, Long>, JpaSpecificationExecutor<Consulta> {

    <T> Page<T> findAllProjectedBy(Class<T> type, Pageable pageable);

    Page<Consulta> findAll(Specification<Consulta> spec, Pageable pageable);

    <T> T findById(Long id, Class<T> type);

    boolean existsByMedicoIdAndDiaAndHora(Long medicoId, LocalDate data, LocalTime hora);

    boolean existsByPacienteIdAndDiaAndHora(Long pacienteId, LocalDate dia, LocalTime hora);

}
