package br.com.ferdbgg.springestudoalura.validator.consulta;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

@Component
public class ValidadorDiaConsulta
        implements ValidadorCadastroConsulta {

    @Override
    public void validar(DadosCadastroConsulta dados) {

        if (isDomingo(dados.dataHora())) {

            throw AgendamentoConsultaException.diaClinicaFechada();

        }

    }

    private boolean isDomingo(OffsetDateTime dataHora) {

        final var dia = dataHora.atZoneSameInstant(DataHoraUtil.BRASILIA).getDayOfWeek();
        
        return DayOfWeek.SUNDAY.equals(dia);
    
    }

}
