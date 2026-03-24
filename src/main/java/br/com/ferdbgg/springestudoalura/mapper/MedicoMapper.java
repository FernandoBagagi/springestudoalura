package br.com.ferdbgg.springestudoalura.mapper;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.domain.entity.Medico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresMedico;
import br.com.ferdbgg.springestudoalura.service.EnderecoService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MedicoMapper {

    private final EnderecoService enderecoService;

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

    public DadosComplementaresMedico parseDadosComplementares(Medico medico) {

        return new DadosComplementaresMedico(
                medico.getEmail(),
                medico.getTelefone(),
                medico.getEndereco());

    }
    
}
