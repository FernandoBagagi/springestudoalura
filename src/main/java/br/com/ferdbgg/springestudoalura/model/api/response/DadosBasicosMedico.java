package br.com.ferdbgg.springestudoalura.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import br.com.ferdbgg.springestudoalura.model.mapper.MedicoMapper;

public record DadosBasicosMedico(

                Long id,

                EspecialidadeMedico especialidade,

                @JsonIgnore //
                Genero genero,

                @JsonIgnore //
                String nome,

                @JsonIgnore //
                String sobrenome,

                String crm

) {

        @Override
        public String nome() {

                return MedicoMapper.buildMedicoNome(genero, nome, sobrenome);

        }

}
