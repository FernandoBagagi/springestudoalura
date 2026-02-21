package br.com.ferdbgg.springestudoalura.service;

import org.springframework.stereotype.Service;
import br.com.ferdbgg.springestudoalura.dto.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.model.Endereco;

@Service
public class EnderecoService {

    public Endereco parseEndereco(DadosCadastroEndereco dados) {

        if (dados == null) {
            return null;
        }

        final Endereco endereco = new Endereco();

        endereco.setLogradouro(dados.logradouro());

        endereco.setBairro(dados.bairro());

        endereco.setCep(dados.cep());

        endereco.setCidade(dados.cidade());

        endereco.setUf(dados.uf());

        endereco.setComplemento(dados.complemento());

        endereco.setNumero(dados.numero());

        return endereco;

    }

}
