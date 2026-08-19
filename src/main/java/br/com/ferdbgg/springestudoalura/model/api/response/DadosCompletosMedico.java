package br.com.ferdbgg.springestudoalura.model.api.response;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import br.com.ferdbgg.springestudoalura.model.enums.PerfilUsuario;

public record DadosCompletosMedico(

        Long id,

        String usuarioEmail,

        String usuarioLogin,

        PerfilUsuario usuarioPerfil,

        Boolean usuarioAtivo,

        Genero genero,

        String nome,

        String sobrenome,

        String crm,

        EspecialidadeMedico especialidade

) {

    public static DadosCompletosMedico empty() {

        return new DadosCompletosMedico(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

    }

}
