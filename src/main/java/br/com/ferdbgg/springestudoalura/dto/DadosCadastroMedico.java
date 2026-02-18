package br.com.ferdbgg.springestudoalura.dto;

import br.com.ferdbgg.springestudoalura.model.EspecialidadeMedico;

public record DadosCadastroMedico(
        String nome,
        String email,
        String crm,
        EspecialidadeMedico especialidade,
        EnderecoDTO endereco) {
}
