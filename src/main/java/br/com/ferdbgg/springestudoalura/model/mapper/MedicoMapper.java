package br.com.ferdbgg.springestudoalura.model.mapper;

import static br.com.ferdbgg.springestudoalura.model.enums.PerfilUsuario.MEDICO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoMedico;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.model.entity.Medico;
import br.com.ferdbgg.springestudoalura.model.entity.Usuario;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoMedicoForm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MedicoMapper {

    private final PasswordEncoder encriptador;

    public Usuario parseUsuario(DadosCadastroMedico dados) {

        if (dados == null) {
            return null;
        }

        final var usuario = new Usuario();
        usuario.setEmail(dados.email());
        usuario.setLogin(dados.login());
        final var senha = encriptador.encode("123456789");
        usuario.setSenha(senha);
        usuario.setPerfil(MEDICO);
        usuario.setAtivo(Boolean.TRUE);

        return usuario;

    }

    public Medico parseMedico(DadosCadastroMedico dados) {

        if (dados == null) {
            return null;
        }

        final var medico = new Medico();
        medico.setGenero(dados.genero());
        medico.setNome(dados.nome());
        medico.setSobrenome(dados.sobrenome());
        medico.setCrm(dados.crm());
        medico.setEspecialidade(dados.especialidade());

        return medico;

    }

    public DadosBasicosMedico parseDadosBasicos(Medico medico) {

        if (medico == null) {
            return null;
        }

        return new DadosBasicosMedico(
                medico.getId(),
                medico.getEspecialidade(),
                medico.getGenero(),
                medico.getNome(),
                medico.getSobrenome(),
                medico.getCrm());

    }

    public DadosCadastroMedico parseDadosCadastro(CadastroEdicaoMedicoForm dados) {

        return new DadosCadastroMedico(
                dados.usuarioEmail(),
                dados.usuarioLogin(),
                dados.genero(),
                dados.nome(),
                dados.sobrenome(),
                dados.crm(),
                dados.especialidade());

    }

    public DadosAtualizacaoMedico parseDadosAtualizacao(CadastroEdicaoMedicoForm dados) {

        return new DadosAtualizacaoMedico(
                dados.id(),
                dados.usuarioEmail(),
                dados.usuarioLogin(),
                dados.senhaAntiga(),
                dados.senhaNova(),
                dados.genero(),
                dados.nome(),
                dados.sobrenome(),
                dados.crm(),
                dados.especialidade());

    }

    public static String buildMedicoNome(Genero genero, String nome, String sobrenome) {
        
        final var buffer = new StringBuilder();
        buffer.append("Dr");
        if (Genero.FEMININO.equals(genero)) {
            buffer.append("a");
        }
        buffer.append(". ");
        buffer.append(nome);
        buffer.append(" ");
        buffer.append(sobrenome);

        return buffer.toString();
        
    }

}
