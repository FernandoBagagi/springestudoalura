package br.com.ferdbgg.springestudoalura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ferdbgg.springestudoalura.entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    <T> Page<T> findByAtivo(Boolean ativo, Class<T> type, Pageable pageable);

}
