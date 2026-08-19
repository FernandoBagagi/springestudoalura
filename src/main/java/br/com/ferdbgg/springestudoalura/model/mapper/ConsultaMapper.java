package br.com.ferdbgg.springestudoalura.model.mapper;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoConsulta;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.model.entity.Consulta;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoConsultaForm;

@Component
public class ConsultaMapper {

    public DadosConsulta parseDadosConsulta(Consulta consulta) {

        return new DadosConsulta(
                consulta.getId(),
                consulta.getDia(),
                consulta.getHora(),
                consulta.getMedico().getId(),
                consulta.getMedico().getEspecialidade(),
                consulta.getMedico().getGenero(),
                consulta.getMedico().getNome(),
                consulta.getMedico().getSobrenome(),
                consulta.getMedico().getCrm(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome());

    }

    public DadosCadastroConsulta parseDadosCadastro(Consulta consulta) {

        return new DadosCadastroConsulta(
                consulta.getMedico().getId(),
                consulta.getMedico().getEspecialidade(),
                consulta.getPaciente().getId(),
                consulta.getDataHora());

    }

    public DadosCadastroConsulta parseDadosCadastro(CadastroEdicaoConsultaForm dados) {

        return new DadosCadastroConsulta(
                dados.medicoId(),
                dados.especialidade(),
                dados.pacienteId(),
                dados.dataHora());

    }

    public DadosAtualizacaoConsulta parseDadosAtualizacao(CadastroEdicaoConsultaForm dados) {

        return new DadosAtualizacaoConsulta(
                dados.id(),
                dados.medicoId(),
                dados.dataHora());

    }

}
