package br.com.ferdbgg.springestudoalura.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ferdbgg.springestudoalura.model.entity.Medico;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    <T> Optional<T> findOneByIdAndUsuarioAtivo(Long id, Boolean ativo, Class<T> type);
    
    <T> Page<T> findPageByUsuarioAtivo(Boolean ativo, Class<T> type, Pageable pageable);

    <T> List<T> findAllByUsuarioAtivoTrue(Class<T> type);

    @Query("""
            SELECT m
            FROM Medico m
            WHERE m.especialidade = :especialidade
            AND m.usuario.ativo = TRUE
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

    Long countByEspecialidadeAndUsuarioAtivoTrue(EspecialidadeMedico especialidade);

}
