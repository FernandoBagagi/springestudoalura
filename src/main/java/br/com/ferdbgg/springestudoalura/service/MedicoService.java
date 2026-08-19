package br.com.ferdbgg.springestudoalura.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoMedico;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;
import br.com.ferdbgg.springestudoalura.model.entity.Medico;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.mapper.MedicoMapper;
import br.com.ferdbgg.springestudoalura.model.mapper.PaginaMapper;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import br.com.ferdbgg.springestudoalura.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoMapper mapper;

    private final PaginaMapper paginaMapper;

    private final UsuarioRepository usuarioRepository;

    private final MedicoRepository medicoRepository;

    private final PasswordEncoder encriptador;

    @Transactional
    public DadosBasicosMedico cadastrar(DadosCadastroMedico dados) {

        var usuario = mapper.parseUsuario(dados);

        usuario = usuarioRepository.save(usuario);

        var medico = mapper.parseMedico(dados);
        medico.setId(usuario.getId());
        medico.setUsuario(usuario);

        medico = medicoRepository.save(medico);

        return mapper.parseDadosBasicos(medico);

    }

    public Pagina<DadosBasicosMedico> listarDadosBasicos(Pageable pageable) {

        final var page = medicoRepository
                .findByUsuarioAtivo(Boolean.TRUE, DadosBasicosMedico.class, pageable);

        return paginaMapper.parsePagina(page);

    }

    public DadosBasicosMedico[] listarTodosDadosBasicos() {

        return medicoRepository
                .findAll(DadosBasicosMedico.class)
                .toArray(new DadosBasicosMedico[0]);

    }

    public <T> Optional<T> pesquisarPorIdAndUsuarioAtivo(Long id, Class<T> type) {

        return medicoRepository
                .findByIdAndUsuarioAtivo(id, Boolean.TRUE, type);

    }

    @Transactional
    public DadosBasicosMedico atualizar(DadosAtualizacaoMedico dados) {

        final var medico = medicoRepository
                .findByIdAndUsuarioAtivo(dados.id(), Boolean.TRUE, Medico.class)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));

        if (dados.email() != null && !dados.email().isBlank()) {
            medico.getUsuario().setEmail(dados.email());
            // TODO: conferir se atualiza o usuario e se as contraints funcionam
        }

        if (dados.login() != null && !dados.login().isBlank()) {
            medico.getUsuario().setLogin(dados.login());
        }

        if (dados.senhaAntiga() != null && !dados.senhaAntiga().isBlank()
                && dados.senhaNova() != null && !dados.senhaNova().isBlank()
                && !dados.senhaAntiga().equals(dados.senhaNova())
                && encriptador.matches(dados.senhaAntiga(), medico.getUsuario().getSenha()) //
        ) {
            medico.getUsuario().setSenha(encriptador.encode(dados.senhaNova()));
        }

        if (dados.genero() != null) {
            medico.setGenero(dados.genero());
        }

        if (dados.nome() != null && !dados.nome().isBlank()) {
            medico.setNome(dados.nome());
        }

        if (dados.sobrenome() != null && !dados.sobrenome().isBlank()) {
            medico.setSobrenome(dados.sobrenome());
        }

        if (dados.crm() != null && !dados.crm().isBlank()) {
            medico.setCrm(dados.crm());
        }

        if (dados.especialidade() != null) {
            medico.setEspecialidade(dados.especialidade());
        }

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

        return mapper.parseDadosBasicos(medico);

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)
        // Exclusão lógica
        medicoRepository
                .getReferenceById(id)
                .getUsuario()
                .setAtivo(Boolean.FALSE);

    }

    public Optional<Medico> procurarMedicoDisponivel(
            EspecialidadeMedico especialidade,
            LocalDate dia,
            LocalTime hora //
    ) {

        return medicoRepository
                .findFirstMedicoDisponivel(especialidade, dia, hora);

    }

}
