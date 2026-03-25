package br.com.ferdbgg.springestudoalura.mapper;

import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresPaciente;

public class PacienteMapper {
    
    private PacienteMapper() {

    }

    public static Paciente parse(DadosCadastroPaciente dados) {

        if (dados == null) {
            return null;
        }

        final Paciente paciente = new Paciente();

        paciente.setNome(dados.nome());

        paciente.setEmail(dados.email());

        paciente.setTelefone(dados.telefone());

        paciente.setCpf(dados.cpf());

        paciente.setAtivo(Boolean.TRUE);

        paciente.setEndereco(EnderecoMapper.parse(dados.endereco()));

        return paciente;

    }

    public static DadosBasicosPaciente parseDadosBasicos(Paciente paciente) {

        if (paciente == null) {
            return null;
        }

        return new DadosBasicosPaciente(paciente.getId(), paciente.getNome());

    }

    public static DadosComplementaresPaciente parseDadosComplementares(Paciente paciente) {

        return new DadosComplementaresPaciente(
                paciente.getEmail(),
                paciente.getCpf(),
                paciente.getTelefone(),
                EnderecoMapper.toString(paciente.getEndereco()));

    }

}
