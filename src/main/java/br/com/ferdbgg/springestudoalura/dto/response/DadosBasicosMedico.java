package br.com.ferdbgg.springestudoalura.dto.response;

import br.com.ferdbgg.springestudoalura.entities.EspecialidadeMedico;

public record DadosBasicosMedico(
        Long id,
        String nome,
        String crm,
        EspecialidadeMedico especialidade) {
}
