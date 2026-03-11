package br.com.ferdbgg.springestudoalura.dto.response;

import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;

public record DadosBasicosMedico(
        Long id,
        String nome,
        String crm,
        EspecialidadeMedico especialidade) {
}
