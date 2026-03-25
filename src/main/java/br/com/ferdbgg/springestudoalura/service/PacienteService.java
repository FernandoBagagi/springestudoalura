package br.com.ferdbgg.springestudoalura.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.domain.entity.Endereco;
import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoMedicoPaciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;
import br.com.ferdbgg.springestudoalura.mapper.EnderecoMapper;
import br.com.ferdbgg.springestudoalura.mapper.PacienteMapper;
import br.com.ferdbgg.springestudoalura.mapper.PaginaMapper;
import br.com.ferdbgg.springestudoalura.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;

    @Transactional
    public DadosBasicosPaciente cadastrar(DadosCadastroPaciente dados) {

        Paciente paciente = PacienteMapper.parse(dados);

        paciente = repository.save(paciente);

        return PacienteMapper.parseDadosBasicos(paciente);

    }

    public Pagina<DadosBasicosPaciente> listar(Pageable pageable) {

        final Page<DadosBasicosPaciente> page = repository
                .findByAtivo(Boolean.TRUE, DadosBasicosPaciente.class, pageable);

        return PaginaMapper.map(page);

    }

    public DadosComplementaresPaciente pesquisarPorId(Long id) {

        return repository
                .findByIdAndAtivo(id, Boolean.TRUE, DadosComplementaresPaciente.class)
                .orElse(null);

    }

    @Transactional
    public DadosBasicosPaciente atualizarCadastro(DadosAtualizacaoMedicoPaciente dados) {

        Paciente paciente = repository.getReferenceById(dados.id());

        if (dados.nome() != null && !dados.nome().isBlank()) {
            paciente.setNome(dados.nome());
        }

        if (dados.telefone() != null) {
            paciente.setTelefone(dados.telefone());
        }

        final Endereco endereco = EnderecoMapper.parse(dados.endereco());
        if (endereco != null) {
            paciente.setEndereco(endereco);
        }

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

        return PacienteMapper.parseDadosBasicos(paciente);

    }

    @Transactional
    public void inativarPorId(Long id) {

        // O comando pra deletar definitivamente é repository.deleteById(id)

        // Exclusão lógica
        final Paciente paciente = repository.getReferenceById(id);
        paciente.setAtivo(Boolean.FALSE);

    }

}
