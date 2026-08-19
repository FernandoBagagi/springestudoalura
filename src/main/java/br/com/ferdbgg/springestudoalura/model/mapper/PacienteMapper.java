package br.com.ferdbgg.springestudoalura.model.mapper;

import static br.com.ferdbgg.springestudoalura.model.enums.PerfilUsuario.PACIENTE;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoEndereco;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoPaciente;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.model.entity.Paciente;
import br.com.ferdbgg.springestudoalura.model.entity.Usuario;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoPacienteForm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private final PasswordEncoder encriptador;
    private final EnderecoMapper enderecoMapper;

    public Usuario parseUsuario(DadosCadastroPaciente dados) {

        if (dados == null) {
            return null;
        }

        final var usuario = new Usuario();
        usuario.setEmail(dados.email());
        usuario.setLogin(dados.login());
        final var senha = encriptador.encode("123456789");
        usuario.setSenha(senha);
        usuario.setPerfil(PACIENTE);
        usuario.setAtivo(Boolean.TRUE);

        return usuario;

    }

    public Paciente parsePaciente(DadosCadastroPaciente dados) {

        if (dados == null) {
            return null;
        }

        final var paciente = new Paciente();
        paciente.setNome(dados.nome());
        paciente.setCpf(dados.cpf());
        paciente.setNascimento(dados.nascimento());
        paciente.setTelefone(dados.telefone());
        paciente.setEndereco(enderecoMapper.parseEndereco(dados.endereco()));

        return paciente;

    }

    public DadosBasicosPaciente parseDadosBasicos(Paciente paciente) {

        if (paciente == null) {
            return null;
        }

        return new DadosBasicosPaciente(
                paciente.getId(),
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getUsuario().getEmail(),
                paciente.getTelefone());

    }

    public DadosCadastroPaciente parseDadosCadastro(CadastroEdicaoPacienteForm dados) {

        return new DadosCadastroPaciente(
                dados.usuarioEmail(),
                dados.usuarioLogin(),
                dados.nome(),
                dados.cpf(),
                dados.nascimento(),
                dados.telefone(),
                new DadosCadastroEndereco(
                        dados.enderecoLogradouro(),
                        dados.enderecoNumero(),
                        dados.enderecoComplemento(),
                        dados.enderecoBairro(),
                        dados.enderecoCidade(),
                        dados.enderecoUf(),
                        dados.enderecoCep()));

    }

    public DadosAtualizacaoPaciente parseDadosAtualizacao(CadastroEdicaoPacienteForm dados) {

        return new DadosAtualizacaoPaciente(
                dados.id(),
                dados.usuarioEmail(),
                dados.usuarioLogin(),
                dados.senhaAntiga(),
                dados.senhaNova(),
                dados.nome(),
                dados.cpf(),
                dados.nascimento(),
                dados.telefone(),
                new DadosAtualizacaoEndereco(
                        dados.enderecoLogradouro(),
                        dados.enderecoNumero(),
                        dados.enderecoComplemento(),
                        dados.enderecoBairro(),
                        dados.enderecoCidade(),
                        dados.enderecoUf(),
                        dados.enderecoCep()));

    }

}
