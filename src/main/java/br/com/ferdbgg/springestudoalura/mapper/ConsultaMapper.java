package br.com.ferdbgg.springestudoalura.mapper;

import br.com.ferdbgg.springestudoalura.domain.entity.Consulta;
import br.com.ferdbgg.springestudoalura.dto.response.DadosConsulta;

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
