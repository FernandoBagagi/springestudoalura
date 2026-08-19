package br.com.ferdbgg.springestudoalura.model.exception;

public class AgendamentoConsultaException extends RuntimeException {

    public AgendamentoConsultaException() {
        super("Não foi possível realizar o agendamento.");
    }

    public AgendamentoConsultaException(String message) {
        super(message);
    }

    public static AgendamentoConsultaException diaClinicaFechada() {
        return new AgendamentoConsultaException(
                "A clínica está fechada no dia informado.");
    }

    public static AgendamentoConsultaException horarioClinicaFechada() {
        return new AgendamentoConsultaException(
                "A clínica está fechada na hora informada.");
    }

    public static AgendamentoConsultaException antecedenciaMinima(int minutos) {
        return new AgendamentoConsultaException(
                "A consulta deve ter uma atencedencia mínima de " + minutos + " minutos.");
    }

    public static AgendamentoConsultaException dadosObrigatorios() {
        return new AgendamentoConsultaException(
                "O médico ou a especialidade devem ser informado para fazer o agendamento.");
    }

    public static AgendamentoConsultaException medicoJaPossuiConsulta() {
        return new AgendamentoConsultaException(
                "O médico já possui uma consulta agendada para esse dia e hora.");
    }

    public static AgendamentoConsultaException pacienteJaPossuiConsulta() {
        return new AgendamentoConsultaException(
                "O paciente já possui uma consulta agendada para esse dia e hora.");
    }

    public static AgendamentoConsultaException medicoNaoDisponivel() {
        return new AgendamentoConsultaException(
                "Nenhum médico dessa especialidade possui disponibilidade para consulta neste dia e hora.");
    }

    public static AgendamentoConsultaException consultaNaoEncontrada() {
        return new AgendamentoConsultaException(
                "Não foi encontrado nenhuma consulta neste dia e hora para o médico e/ou paciente informados.");
    }

    public static AgendamentoConsultaException pacienteNaoEncontrado() {
        return new AgendamentoConsultaException(
                "O paciente não foi encontrado.");
    }

    public static AgendamentoConsultaException medicoNaoEncontrado() {
        return new AgendamentoConsultaException(
                "O médico não foi encontrado.");
    }

}
