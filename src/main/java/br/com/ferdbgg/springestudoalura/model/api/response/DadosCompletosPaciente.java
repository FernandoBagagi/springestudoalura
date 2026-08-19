package br.com.ferdbgg.springestudoalura.model.api.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ferdbgg.springestudoalura.model.entity.Endereco;
import br.com.ferdbgg.springestudoalura.model.enums.PerfilUsuario;

public record DadosCompletosPaciente(

                Long id,

                String usuarioEmail,

                String usuarioLogin,

                PerfilUsuario usuarioPerfil,

                Boolean usuarioAtivo,

                String nome,

                String cpf,

                LocalDate nascimento,

                String telefone,

                @JsonIgnore //
                Endereco endereco

) {

        public String getEnderecoCompleto() {

                return endereco == null ? null : endereco.toString();
                
        }

}
