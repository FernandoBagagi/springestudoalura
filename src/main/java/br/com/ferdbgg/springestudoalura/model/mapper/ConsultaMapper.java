package br.com.ferdbgg.springestudoalura.model.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoConsulta;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.model.entity.Consulta;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoConsultaForm;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

@Component
public class ConsultaMapper {

    public DadosConsulta parseDadosConsulta(Consulta dados) {

        return new DadosConsulta(
                dados.getId(),
                dados.getDia(),
                dados.getHora(),
                dados.getMedico().getId(),
                dados.getMedico().getEspecialidade(),
                dados.getMedico().getGenero(),
                dados.getMedico().getNome(),
                dados.getMedico().getSobrenome(),
                dados.getMedico().getCrm(),
                dados.getPaciente().getId(),
                dados.getPaciente().getNome());

    }

    public DadosCadastroConsulta parseDadosCadastro(Consulta dados) {

        return new DadosCadastroConsulta(
                dados.getMedico().getId(),
                dados.getMedico().getEspecialidade(),
                dados.getPaciente().getId(),
                dados.getDataHora());

    }

    public DadosCadastroConsulta parseDadosCadastro(CadastroEdicaoConsultaForm dados) {

        return new DadosCadastroConsulta(
                dados.medicoId(),
                dados.medicoEspecialidade(),
                dados.pacienteId(),
                DataHoraUtil.converterParaOffsetDateTime(dados.dataHora()));

    }

    public DadosAtualizacaoConsulta parseDadosAtualizacao(CadastroEdicaoConsultaForm dados) {

        return new DadosAtualizacaoConsulta(
                dados.id(),
                dados.medicoId(),
                DataHoraUtil.converterParaOffsetDateTime(dados.dataHora()));

    }

    public CadastroEdicaoConsultaForm parseCadastroEdicaoForm(Consulta dados) {

        return new CadastroEdicaoConsultaForm(
                dados.getId(),
                dados.getMedico().getId(),
                dados.getMedico().getEspecialidade(),
                dados.getPaciente().getId(),
                LocalDateTime.of(
                        dados.getDia(),
                        dados.getHora()));

    }

}
