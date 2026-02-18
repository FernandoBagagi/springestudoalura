package br.com.ferdbgg.springestudoalura.service;

import org.springframework.stereotype.Service;

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.EnderecoDTO;
import br.com.ferdbgg.springestudoalura.model.Endereco;
import br.com.ferdbgg.springestudoalura.model.Medico;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repository;

    public Medico cadastrarNovoMedico(DadosCadastroMedico dados) {
        return repository.save(parseMedico(dados));
    }

    public Medico parseMedico(DadosCadastroMedico dados) {

        if (dados == null) {
            return null;
        }

        final Medico medico = new Medico();

        medico.setNome(dados.nome());

        medico.setEmail(dados.email());

        medico.setCrm(dados.crm());

        medico.setEspecialidade(dados.especialidade());

        medico.setEndereco(parseEndereco(dados.endereco()));

        return medico;

    }

    public Endereco parseEndereco(EnderecoDTO dto) {

        if (dto == null) {
            return null;
        }

        final Endereco endereco = new Endereco();

        endereco.setLogradouro(dto.logradouro());

        endereco.setBairro(dto.bairro());

        endereco.setCep(dto.cep());

        endereco.setCidade(dto.cidade());

        endereco.setUf(dto.uf());

        endereco.setComplemento(dto.complemento());

        endereco.setNumero(dto.numero());

        return endereco;

    }

}
