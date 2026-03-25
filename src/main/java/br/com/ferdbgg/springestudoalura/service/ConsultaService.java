package br.com.ferdbgg.springestudoalura.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.mapper.ConsultaMapper;
import br.com.ferdbgg.springestudoalura.mapper.PaginaMapper;
import br.com.ferdbgg.springestudoalura.repository.ConsultaRepository;
import br.com.ferdbgg.springestudoalura.repository.MedicoRepository;
import br.com.ferdbgg.springestudoalura.repository.PacienteRepository;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;
import br.com.ferdbgg.springestudoalura.validator.consulta.ValidadorAgendamentoConsulta;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    private final List<ValidadorAgendamentoConsulta> validadoresAgendamento;

    @Transactional
    public DadosConsulta agendar(DadosAgendamentoConsulta dados) {

        validadoresAgendamento.forEach(v -> v.validar(dados));

        final Medico medico = procurarMedicoPorDados(dados);

        final LocalDate dia = DataHoraUtil.converterParaLocalDate(dados.dataHora());
        final LocalTime hora = DataHoraUtil.converterParaLocalTime(dados.dataHora());

        if (medico == null) {
            throw AgendamentoConsultaException.medicoNaoEncontrado();
        }

        if (jaExisteConsultaMarcadaPara(medico, dia, hora)) {
            throw AgendamentoConsultaException.medicoJaPossuiConsulta();
        }

        final Paciente paciente = pacienteRepository
                .getReferenceByIdAndAtivoTrue(dados.idPaciente());

        if (paciente == null) {
            throw AgendamentoConsultaException.pacienteNaoEncontrado();
        }

        if (jaExisteConsultaMarcadaPara(paciente, dia, hora)) {
            throw AgendamentoConsultaException.pacienteJaPossuiConsulta();
        }

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDia(dia);
        consulta.setHora(hora);

        consulta = consultaRepository.save(consulta);

        return ConsultaMapper.parseDadosConsulta(consulta);

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

    private boolean jaExisteConsultaMarcadaPara(Paciente paciente, LocalDate dia, LocalTime hora) {

        return consultaRepository.existsByPacienteIdAndDiaAndHora(paciente.getId(), dia, hora);

    }

    private Medico procurarMedicoPorId(Long id) {

        return medicoRepository.getReferenceByIdAndAtivoTrue(id);

    }

    private Medico procurarMedicoDisponivelEspecialidade(DadosAgendamentoConsulta dados) {

        return medicoRepository
                .findFirstMedicoDisponivel(
                        dados.especialidade(),
                        DataHoraUtil.converterParaLocalDate(dados.dataHora()),
                        DataHoraUtil.converterParaLocalTime(dados.dataHora()))
                .orElseThrow(AgendamentoConsultaException::medicoNaoDisponivel);

    }

    public Pagina<DadosConsulta> listar(Pageable pageable) {

        final Page<DadosConsulta> page = consultaRepository
                .findAllProjectedBy(DadosConsulta.class, pageable);

        return PaginaMapper.map(page);

    }

    public DadosConsulta pesquisarPorId(Long id) {

        return consultaRepository.findById(id, DadosConsulta.class);

    }

    @Transactional
    public DadosConsulta atualizarAgendamento(DadosAtualizacaoAgendamentoConsulta dados) {

        final Consulta consulta = consultaRepository
                .findById(dados.id())
                .orElseThrow(AgendamentoConsultaException::consultaNaoEncontrada);

        if (!Boolean.TRUE.equals(consulta.getPaciente().getAtivo())) {
            throw AgendamentoConsultaException.pacienteNaoEncontrado();
        }

        final Medico medico = getMedicoOuProcurarPorId(consulta.getMedico(), dados.idMedico());

        if (medico == null) {
            throw AgendamentoConsultaException.medicoNaoEncontrado();
        }

        final LocalDate dia = DataHoraUtil.converterParaLocalDate(dados.dataHora());
        final LocalTime hora = DataHoraUtil.converterParaLocalTime(dados.dataHora());

        if (jaExisteConsultaMarcadaPara(medico, dia, hora)) {
            throw AgendamentoConsultaException.medicoJaPossuiConsulta();
        }

        consulta.setMedico(medico);
        consulta.setDia(dia);
        consulta.setHora(hora);

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

        return ConsultaMapper.parseDadosConsulta(consulta);

    }

    private Medico getMedicoOuProcurarPorId(Medico medico, Long idMedico) {

        if (!Objects.equals(medico.getId(), idMedico)) {
            return procurarMedicoPorId(idMedico);
        }

        return Boolean.TRUE.equals(medico.getAtivo()) ? medico : null;

    }

    @Transactional
    public void cancelarAgendamentoPorId(Long id) {

        consultaRepository.deleteById(id);

    }

}
