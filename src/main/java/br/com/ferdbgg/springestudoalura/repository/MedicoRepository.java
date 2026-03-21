package br.com.ferdbgg.springestudoalura.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ferdbgg.springestudoalura.domain.entity.Medico;
import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Query("SELECT m.id FROM Medico m WHERE m.id = :id AND m.ativo = TRUE")
    Medico getReferenceByIdAndAtivoTrue(Long id);

    <T> Page<T> findByAtivo(Boolean ativo, Class<T> type, Pageable pageable);

    <T> Optional<T> findByIdAndAtivo(Long id, Boolean ativo, Class<T> type);

    @Query("""
            SELECT m.id
            FROM Medico m
            WHERE m.especialidade = :especialidade
            AND m.ativo = TRUE
            AND NOT EXISTS (
                SELECT c
                FROM Consulta c
                WHERE c.medico = m
                AND c.dia = :dia
                AND c.hora = :hora
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Optional<Medico> findFirstMedicoDisponivel(
            EspecialidadeMedico especialidade,
            LocalDate dia,
            LocalTime hora);

}
