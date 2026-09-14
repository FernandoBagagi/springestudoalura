package br.com.ferdbgg.springestudoalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.model.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    <T> Optional<T> findOneByIdAndUsuarioAtivo(Long id, Boolean ativo, Class<T> type);
    
    <T> Page<T> findPageByUsuarioAtivo(Boolean ativo, Class<T> type, Pageable pageable);

    <T> List<T> findAllByUsuarioAtivoTrue(Class<T> type);

}
