package br.com.ferdbgg.springestudoalura.model.api.response;

public record DadosBasicosPaciente(

                Long id,

                String cpf,
                
                String nome,

                String usuarioEmail,

                String telefone

) {
}
