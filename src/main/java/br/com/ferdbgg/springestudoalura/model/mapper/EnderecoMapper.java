package br.com.ferdbgg.springestudoalura.model.mapper;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoEndereco;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.model.entity.Endereco;

@Component
public class EnderecoMapper {

    public Endereco parseEndereco(DadosCadastroEndereco dados) {

        if (dados == null) {
            return null;
        }

        final var endereco = new Endereco();

        endereco.setLogradouro(dados.logradouro());
        
        endereco.setNumero(dados.numero());

        endereco.setComplemento(dados.complemento());
        
        endereco.setBairro(dados.bairro());

        endereco.setCidade(dados.cidade());
        
        endereco.setUf(dados.uf());
        
        endereco.setCep(dados.cep());

        return endereco;

    }

    public Endereco parseEndereco(DadosAtualizacaoEndereco dados) {

        if (dados == null) {
            return null;
        }

        final var endereco = new Endereco();

        endereco.setLogradouro(dados.logradouro());
        
        endereco.setNumero(dados.numero());

        endereco.setComplemento(dados.complemento());
        
        endereco.setBairro(dados.bairro());

        endereco.setCidade(dados.cidade());
        
        endereco.setUf(dados.uf());
        
        endereco.setCep(dados.cep());

        return endereco;

    }

}
