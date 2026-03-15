package br.com.ferdbgg.springestudoalura.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.domain.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    <T> Page<T> findAll(Class<T> type, Pageable pageable);

    <T> T findById(Long id, Class<T> type);

    boolean existsByMedicoIdAndDiaAndHora(Long medicoId, LocalDate data, LocalTime hora);

}
