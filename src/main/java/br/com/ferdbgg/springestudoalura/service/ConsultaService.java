package br.com.ferdbgg.springestudoalura.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.domain.entity.Consulta;
import br.com.ferdbgg.springestudoalura.domain.entity.Medico;
import br.com.ferdbgg.springestudoalura.domain.entity.Paciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.repository.ConsultaRepository;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;

    @Transactional
    public Consulta agendar(DadosAgendamentoConsulta dados) {

        final Medico medico = procurarMedicoPorDados(dados);

        final LocalDate dia = dados.dataHora().toLocalDate();
        final LocalTime hora = dados.dataHora().toLocalTime();

        if (jaExisteConsultaMarcadaPara(medico, dia, hora)) {
            throw new AgendamentoConsultaException();
        }

        final Paciente paciente = new Paciente(dados.idPaciente());

        final Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDia(dia);
        consulta.setHora(hora);

        return consultaRepository.save(consulta);

    }

    private Medico procurarMedicoPorDados(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null) {
            return procurarMedicoPorId(dados.idMedico());
        }

        if (dados.especialidade() != null) {
            return procurarMedicoDisponivelEspecialidade(dados);
        }

        throw AgendamentoConsultaException.dadosObrigatorios();

    }

    private boolean jaExisteConsultaMarcadaPara(Medico medico, LocalDate dia, LocalTime hora) {

        return consultaRepository.existsByMedicoIdAndDiaAndHora(medico.getId(), dia, hora);

    }

    private Medico procurarMedicoPorId(Long id) {

        return medicoRepository.getReferenceById(id);

    }

    private Medico procurarMedicoDisponivelEspecialidade(DadosAgendamentoConsulta dados) {

        return medicoRepository
                .findFirstMedicoDisponivel(
                        dados.especialidade(),
                        dados.dataHora().toLocalDate(),
                        dados.dataHora().toLocalTime())
                .orElseThrow(AgendamentoConsultaException::new);

    }

    public Page<DadosConsulta> listar(Pageable pageable) {

        return consultaRepository.findAllProjectedBy(DadosConsulta.class, pageable);

    }

    public DadosConsulta pesquisarPorId(Long id) {

        return consultaRepository.findById(id, DadosConsulta.class);

    }

    @Transactional
    public void atualizarAgendamento(DadosAtualizacaoAgendamentoConsulta dados) {

        final Medico medico = procurarMedicoPorId(dados.idMedico());
        final LocalDate dia = dados.dataHora().toLocalDate();
        final LocalTime hora = dados.dataHora().toLocalTime();

        if (jaExisteConsultaMarcadaPara(medico, dia, hora)) {
            throw new AgendamentoConsultaException();
        }

        final Consulta consulta = consultaRepository.getReferenceById(dados.id());

        consulta.setMedico(medico);
        consulta.setDia(dia);
        consulta.setHora(hora);

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

    }

    @Transactional
    public void cancelarAgendamentoPorId(Long id) {

        consultaRepository.deleteById(id);

    }

}
