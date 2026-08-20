package br.com.ferdbgg.springestudoalura.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoPaciente;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;
import br.com.ferdbgg.springestudoalura.model.entity.Paciente;
import br.com.ferdbgg.springestudoalura.model.mapper.EnderecoMapper;
import br.com.ferdbgg.springestudoalura.model.mapper.PacienteMapper;
import br.com.ferdbgg.springestudoalura.model.mapper.PaginaMapper;
import br.com.ferdbgg.springestudoalura.repository.PacienteRepository;
import br.com.ferdbgg.springestudoalura.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteMapper pacienteMapper;

    private final EnderecoMapper enderecoMapper;

    private final PaginaMapper paginaMapper;

    private final UsuarioRepository usuarioRepository;

    private final PacienteRepository pacienteRepository;

    private final PasswordEncoder encriptador;

    @Transactional
    public DadosBasicosPaciente cadastrar(DadosCadastroPaciente dados) {

        var usuario = pacienteMapper.parseUsuario(dados);

        usuario = usuarioRepository.save(usuario);

        var paciente = pacienteMapper.parsePaciente(dados);
        paciente.setUsuario(usuario);

        paciente = pacienteRepository.save(paciente);

        return pacienteMapper.parseDadosBasicos(paciente);

    }

    public Pagina<DadosBasicosPaciente> listarDadosBasicos(Pageable pageable) {

        final var page = pacienteRepository
                .findByUsuarioAtivo(Boolean.TRUE, DadosBasicosPaciente.class, pageable);

        return paginaMapper.parsePagina(page);

    }

    public DadosBasicosPaciente[] listarTodosDadosBasicos() {

        return pacienteRepository
                .findByUsuarioAtivoTrue(DadosBasicosPaciente.class)
                .toArray(new DadosBasicosPaciente[0]);
                
    }

    public <T> Optional<T> pesquisarPorIdAndUsuarioAtivo(Long id, Class<T> type) {

        return pacienteRepository
                .findByIdAndUsuarioAtivo(id, Boolean.TRUE, type);

    }

    @Transactional
    public DadosBasicosPaciente atualizar(DadosAtualizacaoPaciente dados) {

        final var paciente = pacienteRepository
                .findByIdAndUsuarioAtivo(dados.id(), Boolean.TRUE, Paciente.class)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        if (dados.email() != null && !dados.email().isBlank()) {
            paciente.getUsuario().setEmail(dados.email());
        }

        if (dados.login() != null && !dados.login().isBlank()) {
            paciente.getUsuario().setLogin(dados.login());
        }

        if (dados.senhaAntiga() != null && !dados.senhaAntiga().isBlank()
                && dados.senhaNova() != null && !dados.senhaNova().isBlank()
                && !dados.senhaAntiga().equals(dados.senhaNova())
                && encriptador.matches(dados.senhaAntiga(), paciente.getUsuario().getSenha()) //
        ) {
            paciente.getUsuario().setSenha(encriptador.encode(dados.senhaNova()));
        }

        if (dados.nome() != null && !dados.nome().isBlank()) {
            paciente.setNome(dados.nome());
        }

        if (dados.cpf() != null) {
            paciente.setCpf(dados.cpf());
        }

        if (dados.nascimento() != null) {
            paciente.setNascimento(dados.nascimento());
        }

        if (dados.telefone() != null) {
            paciente.setTelefone(dados.telefone());
        }

        final var endereco = enderecoMapper.parseEndereco(dados.endereco());
        if (endereco != null) {
            paciente.setEndereco(endereco);
        }

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

        return pacienteMapper.parseDadosBasicos(paciente);

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)
        // Exclusão lógica
        pacienteRepository
                .getReferenceById(id)
                .getUsuario()
                .setAtivo(Boolean.FALSE);

    }

}
