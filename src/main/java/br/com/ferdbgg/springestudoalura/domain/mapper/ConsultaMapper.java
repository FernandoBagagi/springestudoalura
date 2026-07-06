package br.com.ferdbgg.springestudoalura.domain.mapper;

import br.com.ferdbgg.springestudoalura.domain.dto.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.domain.entity.Consulta;

public class ConsultaMapper {

    private ConsultaMapper() {

    }

    public static DadosConsulta parseDadosConsulta(Consulta consulta) {

        return new DadosConsulta(
                consulta.getId(),
                consulta.getDia(),
                consulta.getHora(),

                consulta.getMedico().getId(),
                consulta.getMedico().getNome(),
                consulta.getMedico().getEspecialidade(),
                consulta.getMedico().getCrm(),

                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome());

    }

}
