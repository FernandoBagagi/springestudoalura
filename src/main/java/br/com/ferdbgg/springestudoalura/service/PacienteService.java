package br.com.ferdbgg.springestudoalura.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.dto.DadosAtualizacao;
import br.com.ferdbgg.springestudoalura.dto.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.dto.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.dto.DadosComplementaresPaciente;
import br.com.ferdbgg.springestudoalura.entities.Endereco;
import br.com.ferdbgg.springestudoalura.entities.Paciente;
import br.com.ferdbgg.springestudoalura.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final EnderecoService enderecoService;

    @Transactional
    public Paciente cadastrar(DadosCadastroPaciente dados) {

        return repository.save(parsePaciente(dados));
    
    }

    public Paciente parsePaciente(DadosCadastroPaciente dados) {

        if (dados == null) {
            return null;
        }

        final Paciente paciente = new Paciente();

        paciente.setNome(dados.nome());

        paciente.setEmail(dados.email());

        paciente.setTelefone(dados.telefone());

        paciente.setCpf(dados.cpf());

        paciente.setAtivo(Boolean.TRUE);

        paciente.setEndereco(enderecoService.parseEndereco(dados.endereco()));

        return paciente;

    }

    public DadosBasicosPaciente parseDadosBasicos(Paciente paciente) {

        if (paciente == null) {
            return null;
        }

        return new DadosBasicosPaciente(paciente.getId(), paciente.getNome());

    }

    public Page<DadosBasicosPaciente> listar(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(this::parseDadosBasicos);
    }

    public DadosComplementaresPaciente parseDadosComplementares(Paciente paciente) {

        return new DadosComplementaresPaciente(
                paciente.getEmail(),
                paciente.getCpf(),
                paciente.getTelefone(),
                enderecoService
                        .parseString(paciente.getEndereco()));

    }

    public DadosComplementaresPaciente pesquisarPorId(Long id) {

        final Optional<Paciente> paciente = repository.findById(id);

        if (!paciente.isPresent()) {
            return null;
        }

        return parseDadosComplementares(paciente.get());

    }

    @Transactional
    public void atualizarCadastro(DadosAtualizacao dados) {

        Paciente paciente = repository.getReferenceById(dados.id());

        if (dados.nome() != null && !dados.nome().isBlank()) {
            paciente.setNome(dados.nome());
        }

        if (dados.telefone() != null) {
            paciente.setTelefone(dados.telefone());
        }

        final Endereco endereco = enderecoService.parseEndereco(dados.endereco());
        if (endereco != null) {
            paciente.setEndereco(endereco);
        }

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)

        // Exclusão lógica
        Paciente paciente = repository.getReferenceById(id);
        paciente.setAtivo(Boolean.FALSE);

    }

}
