package br.com.ferdbgg.springestudoalura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.model.Paciente;
import br.com.ferdbgg.springestudoalura.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final EnderecoService enderecoService;

    @Transactional
    public Paciente cadastrarNovoPaciente(DadosCadastroPaciente dados) {
        return repository.save(parseMedico(dados));
    }

    public Paciente parseMedico(DadosCadastroPaciente dados) {

        if (dados == null) {
            return null;
        }

        final Paciente paciente = new Paciente();

        paciente.setNome(dados.nome());

        paciente.setEmail(dados.email());

        paciente.setTelefone(dados.telefone());

        paciente.setCpf(dados.cpf());

        paciente.setEndereco(enderecoService.parseEndereco(dados.endereco()));

        return paciente;

    }

}
