package br.com.ferdbgg.springestudoalura.service;

import org.springframework.stereotype.Service;
import br.com.ferdbgg.springestudoalura.dto.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.entities.Endereco;

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

    public String parseString(Endereco endereco) {

        if (endereco == null) {
            return null;
        }
        
        final String virgula = ",";
        final String espaco = " ";

        StringBuilder buffer = new StringBuilder();
        
        buffer.append(endereco.getLogradouro());
        buffer.append(virgula);
        buffer.append(espaco);

        final String numero = endereco.getNumero();
        buffer.append(numero == null || numero.isBlank() ? "S/N" : numero);
        buffer.append(virgula);
        buffer.append(espaco);

        final String complemento = endereco.getComplemento();
        if (complemento != null && !complemento.isBlank()) {
            buffer.append(complemento);
            buffer.append(virgula);
            buffer.append(espaco);
        }

        buffer.append(endereco.getBairro());
        buffer.append(virgula);
        buffer.append(espaco);

        buffer.append(endereco.getCidade());
        buffer.append("-");
        buffer.append(endereco.getUf());
        buffer.append(virgula);
        buffer.append(espaco);

        buffer.append(endereco.getCep());

        return buffer.toString();

    }

}
