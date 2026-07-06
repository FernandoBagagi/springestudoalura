package br.com.ferdbgg.springestudoalura.service;

import br.com.ferdbgg.springestudoalura.domain.dto.request.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.domain.entity.Endereco;

public interface EnderecoService {

    public Endereco parseEndereco(DadosCadastroEndereco dados);

    public String parseString(Endereco endereco);

}
