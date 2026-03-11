package br.com.ferdbgg.springestudoalura.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.DadosAtualizacao;
import br.com.ferdbgg.springestudoalura.dto.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.DadosComplementaresMedico;
import br.com.ferdbgg.springestudoalura.entities.Endereco;
import br.com.ferdbgg.springestudoalura.entities.Medico;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repository;
    private final EnderecoService enderecoService;

    @Transactional
    public DadosBasicosMedico cadastrar(DadosCadastroMedico dados) {

        final Medico medico = repository.save(parseMedico(dados));

        return parseDadosBasicos(medico);

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
                medico.getEspecialidade());

    }

    public Page<DadosBasicosMedico> listar(Pageable pageable) {

        return repository.findByAtivo(true, DadosBasicosMedico.class, pageable);

    }

    public DadosComplementaresMedico parseDadosComplementares(Medico medico) {

        return new DadosComplementaresMedico(
                medico.getEmail(),
                medico.getTelefone(),
                medico.getEndereco());

    }

    public DadosComplementaresMedico pesquisarPorId(Long id) {

        return repository.findByIdAndAtivo(id, Boolean.TRUE);

    }

    @Transactional
    public DadosBasicosMedico atualizarCadastro(DadosAtualizacao dados) {

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

        return parseDadosBasicos(medico);

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)

        // Exclusão lógica
        final Medico medico = repository.getReferenceById(id);
        medico.setAtivo(Boolean.FALSE);

    }

}
