package br.com.ferdbgg.springestudoalura.service;

import br.com.ferdbgg.springestudoalura.domain.entity.Endereco;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroEndereco;

public interface EnderecoService {

    public Endereco parseEndereco(DadosCadastroEndereco dados);

    public String parseString(Endereco endereco);

}
