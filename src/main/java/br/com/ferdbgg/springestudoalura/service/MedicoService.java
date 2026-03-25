package br.com.ferdbgg.springestudoalura.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.domain.entity.Endereco;
import br.com.ferdbgg.springestudoalura.domain.entity.Medico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoMedicoPaciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresMedico;
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;
import br.com.ferdbgg.springestudoalura.mapper.MedicoMapper;
import br.com.ferdbgg.springestudoalura.mapper.PaginaMapper;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repository;
    private final MedicoMapper mapper;
    private final EnderecoService enderecoService;

    @Transactional
    public DadosBasicosMedico cadastrar(DadosCadastroMedico dados) {

        final Medico medico = repository.save(mapper.parseMedico(dados));

        return mapper.parseDadosBasicos(medico);

    }

    public Pagina<DadosBasicosMedico> listar(Pageable pageable) {

        final Page<DadosBasicosMedico> page = repository
                .findByAtivo(true, DadosBasicosMedico.class, pageable);

        return PaginaMapper.map(page);

    }

    public DadosComplementaresMedico pesquisarPorId(Long id) {

        return repository
                .findByIdAndAtivo(id, Boolean.TRUE, DadosComplementaresMedico.class)
                .orElse(null);

    }

    @Transactional
    public DadosBasicosMedico atualizarCadastro(DadosAtualizacaoMedicoPaciente dados) {

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

        return mapper.parseDadosBasicos(medico);

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)

        // Exclusão lógica
        final Medico medico = repository.getReferenceById(id);
        medico.setAtivo(Boolean.FALSE);

    }

}
