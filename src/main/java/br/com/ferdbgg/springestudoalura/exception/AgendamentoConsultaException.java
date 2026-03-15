package br.com.ferdbgg.springestudoalura.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgendamentoConsultaException extends RuntimeException {

    private final String message;

    public AgendamentoConsultaException() {
        this.message = "Não foi possível realizar o agendamento.";
    }

    public static AgendamentoConsultaException dadosObrigatorios() {
        return new AgendamentoConsultaException(
                "O médico ou a especialidade devem ser informado para fazer o agendamento.");
    }

}
