package br.com.ferdbgg.springestudoalura.validator.consulta;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

@Component
public class ValidadorHorarioConsulta
        implements ValidadorCadastroConsulta {

    @Override
    public void validar(DadosCadastroConsulta dados) {

        if (isHorarioClinicaFechada(dados.dataHora())) {

            throw AgendamentoConsultaException.horarioClinicaFechada();

        }

    }

    private boolean isHorarioClinicaFechada(OffsetDateTime dataHora) {

        final var hora = DataHoraUtil.converterParaLocalTime(dataHora).getHour();
        
        return hora < 7 && 19 <= hora; // Último horário válido é 18h45
    
    }

}
