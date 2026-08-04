package br.com.ferdbgg.springestudoalura.domain.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.domain.dto.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.domain.dto.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.domain.dto.response.DadosComplementaresPaciente;
import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;
import br.com.ferdbgg.springestudoalura.domain.entity.Usuario;
import static br.com.ferdbgg.springestudoalura.domain.enums.PerfilUsuario.PACIENTE;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private final PasswordEncoder encriptador;

    public Paciente parse(DadosCadastroPaciente dados) {

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

    public DadosBasicosPaciente parseDadosBasicos(Paciente paciente) {

        if (paciente == null) {
            return null;
        }

        return new DadosBasicosPaciente(paciente.getId(), paciente.getNome());

    }

    public DadosComplementaresPaciente parseDadosComplementares(Paciente paciente) {

        return new DadosComplementaresPaciente(
                paciente.getEmail(),
                paciente.getCpf(),
                paciente.getTelefone(),
                EnderecoMapper.toString(paciente.getEndereco()));

    }

    public Usuario parseUsuario(DadosCadastroPaciente dados) {

        if (dados == null) {
            return null;
        }

        final var usuario = new Usuario();
        usuario.setLogin(dados.email());
        final var senha = encriptador.encode(dados.cpf());
        usuario.setSenha(senha);
        usuario.setPerfil(PACIENTE);

        return usuario;

    }

}
