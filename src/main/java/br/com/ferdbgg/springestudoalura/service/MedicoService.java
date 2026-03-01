package br.com.ferdbgg.springestudoalura.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.DadosAtualizacao;
import br.com.ferdbgg.springestudoalura.dto.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.DadosComplementaresMedico;
import br.com.ferdbgg.springestudoalura.model.Endereco;
import br.com.ferdbgg.springestudoalura.model.Medico;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repository;
    private final EnderecoService enderecoService;

    @Transactional
    public Medico cadastrar(DadosCadastroMedico dados) {

        return repository.save(parseMedico(dados));

    }

    public Medico parseMedico(DadosCadastroMedico dados) {

        if (dados == null) {
            return null;
        }

        final Medico medico = new Medico();

        medico.setNome(dados.nome());

        medico.setEmail(dados.email());

        medico.setTelefone(dados.telefone());

        medico.setCrm(dados.crm());

        medico.setAtivo(Boolean.TRUE);

        medico.setEspecialidade(dados.especialidade());

        medico.setEndereco(enderecoService.parseEndereco(dados.endereco()));

        return medico;

    }

    public DadosBasicosMedico parseDadosBasicos(Medico medico) {

        if (medico == null) {
            return null;
        }

        return new DadosBasicosMedico(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade().toString());

    }

    public Page<DadosBasicosMedico> listar(Pageable pageable) {

        return repository
                .findAllByAtivo(pageable, true)
                .map(this::parseDadosBasicos);

    }

    public DadosComplementaresMedico parseDadosComplementares(Medico medico) {

        return new DadosComplementaresMedico(
                medico.getEmail(),
                medico.getTelefone(),
                enderecoService
                        .parseString(medico.getEndereco()));

    }

    public DadosComplementaresMedico pesquisarPorId(Long id) {

        final Optional<Medico> medico = repository.findById(id);

        if (!medico.isPresent()) {
            return null;
        }

        return parseDadosComplementares(medico.get());

    }

    @Transactional
    public void atualizarCadastro(DadosAtualizacao dados) {

        Medico medico = repository.getReferenceById(dados.id());

        if (dados.nome() != null && !dados.nome().isBlank()) {
            medico.setNome(dados.nome());
        }

        if (dados.telefone() != null) {
            medico.setTelefone(dados.telefone());
        }

        final Endereco endereco = enderecoService.parseEndereco(dados.endereco());
        if (endereco != null) {
            medico.setEndereco(endereco);
        }

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)

        // Exclusão lógica
        Medico medico = repository.getReferenceById(id);
        medico.setAtivo(Boolean.FALSE);

    }

}
