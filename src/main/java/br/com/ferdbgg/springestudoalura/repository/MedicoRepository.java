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

    <T> Page<T> findByUsuarioAtivo(Boolean ativo, Class<T> type, Pageable pageable);
    
    <T> Optional<T> findByIdAndUsuarioAtivo(Long id, Boolean ativo, Class<T> type);

    <T> List<T> findAll(Class<T> type); // Testar Example<S>

    @Query("""
            SELECT m.id
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
