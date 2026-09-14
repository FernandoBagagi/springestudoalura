package br.com.ferdbgg.springestudoalura.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoConsulta;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosFiltroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;
import br.com.ferdbgg.springestudoalura.model.entity.Consulta;
import br.com.ferdbgg.springestudoalura.model.entity.Medico;
import br.com.ferdbgg.springestudoalura.model.entity.Paciente;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.PerfilUsuario;
import br.com.ferdbgg.springestudoalura.model.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.model.mapper.ConsultaMapper;
import br.com.ferdbgg.springestudoalura.model.mapper.PaginaMapper;
import br.com.ferdbgg.springestudoalura.repository.ConsultaRepository;
import br.com.ferdbgg.springestudoalura.repository.specification.ConsultaSpecifications;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;
import static br.com.ferdbgg.springestudoalura.validator.consulta.ValidadorAntecedenciaConsulta.MINUTOS_ANTECEDENCIA;
import br.com.ferdbgg.springestudoalura.validator.consulta.ValidadorCadastroConsulta;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaMapper consultaMapper;
    private final PaginaMapper paginaMapper;

    private final ConsultaRepository consultaRepository;

    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    private final List<ValidadorCadastroConsulta> validadores;

    @PreAuthorize("""
            hasAuthority('ATENDENTE')
            OR (hasAuthority('PACIENTE') AND authentication.principal.id == #dados.pacienteId)
            OR (hasAuthority('MEDICO') AND authentication.principal.id == #dados.medicoId)
            """)
    @Transactional
    public DadosConsulta cadastrar(DadosCadastroConsulta dados) {

        validadores.forEach(v -> v.validar(dados));

        final var dia = DataHoraUtil.converterParaLocalDate(dados.dataHora());
        final var hora = DataHoraUtil.converterParaLocalTime(dados.dataHora());

        final var medico = procurarMedicoPorIdOuDisponibilidade(
                dados.medicoId(),
                dados.especialidade(),
                dia,
                hora);

        if (jaExisteConsultaMarcadaPara(medico, dia, hora)) {
            throw AgendamentoConsultaException.medicoJaPossuiConsulta();
        }

        final var paciente = pacienteService
                .pesquisarPorIdAndUsuarioAtivo(dados.pacienteId(), Paciente.class)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado."));

        if (jaExisteConsultaMarcadaPara(paciente, dia, hora)) {
            throw AgendamentoConsultaException.pacienteJaPossuiConsulta();
        }

        var consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDia(dia);
        consulta.setHora(hora);

        consulta = consultaRepository.save(consulta);

        return consultaMapper.parseDadosConsulta(consulta);

    }

    private Medico procurarMedicoPorIdOuDisponibilidade(
            Long id,
            EspecialidadeMedico especialidade,
            LocalDate dia,
            LocalTime hora //
    ) {

        if (id != null) {

            return medicoService
                    .pesquisarPorIdAndUsuarioAtivo(id, Medico.class)
                    .orElseThrow(() -> new EntityNotFoundException("Medico não encontrado."));

        }

        if (especialidade != null) {

            return medicoService
                    .procurarMedicoDisponivel(especialidade, dia, hora)
                    .orElseThrow(AgendamentoConsultaException::medicoNaoDisponivel);

        }

        throw AgendamentoConsultaException.dadosObrigatorios();

    }

    private boolean jaExisteConsultaMarcadaPara(Medico medico, LocalDate dia, LocalTime hora) {

        return consultaRepository.existsByMedicoIdAndDiaAndHora(medico.getId(), dia, hora);

    }

    private boolean jaExisteConsultaMarcadaPara(Paciente paciente, LocalDate dia, LocalTime hora) {

        return consultaRepository.existsByPacienteIdAndDiaAndHora(paciente.getId(), dia, hora);

    }

    @PreAuthorize("""
            hasAuthority('ATENDENTE')
            OR (hasAuthority('PACIENTE') AND authentication.principal.id == #filtro.pacienteId)
            OR (hasAuthority('MEDICO') AND authentication.principal.id == #filtro.medicoId)
            """)
    public Pagina<DadosConsulta> listar(DadosFiltroConsulta filtro, Pageable pageable) {

        final var page = consultaRepository
                .findAll(ConsultaSpecifications.buildSpecifications(filtro), pageable)
                .map(consultaMapper::parseDadosConsulta);

        return paginaMapper.parsePagina(page);

    }

    @PreAuthorize("hasAnyAuthority('ATENDENTE', 'PACIENTE', 'MEDICO')")
    public <T> Optional<T> pesquisarPorIdAndUsuarioId(
            Long id,
            Long usuarioId,
            PerfilUsuario usuarioPerfil,
            Class<T> type //
    ) {

        return switch (usuarioPerfil) {
            case PACIENTE -> consultaRepository.findOneByIdAndPacienteId(id, usuarioId, type);
            case MEDICO -> consultaRepository.findOneByIdAndMedicoId(id, usuarioId, type);
            case ATENDENTE -> consultaRepository.findOneById(id, type);
        };

    }

    @PreAuthorize("hasAnyAuthority('ATENDENTE', 'PACIENTE', 'MEDICO')")
    @Transactional
    public DadosConsulta atualizar(
            DadosAtualizacaoConsulta dados,
            Long usuarioId,
            PerfilUsuario usuarioPerfil //
    ) {

        final var consulta = pesquisarPorIdAndUsuarioId(dados.id(), usuarioId, usuarioPerfil, Consulta.class)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada."));

        if (!Boolean.TRUE.equals(consulta.getPaciente().getUsuario().getAtivo())) {
            throw new EntityNotFoundException("Paciente não encontrado.");
        }

        final var medico = getMedicoOuProcurarPorId(consulta.getMedico(), dados.medicoId())
                .orElseThrow(() -> new EntityNotFoundException("Medico não encontrado."));

        final var dia = DataHoraUtil.converterParaLocalDate(dados.dataHora());
        final var hora = DataHoraUtil.converterParaLocalTime(dados.dataHora());

        if (jaExisteConsultaMarcadaPara(medico, dia, hora)) {
            throw AgendamentoConsultaException.medicoJaPossuiConsulta();
        }

        consulta.setMedico(medico);
        consulta.setDia(dia);
        consulta.setHora(hora);

        // Não precisa de save
        // Ao final da transação, detecta e salva as alterações automaticamente

        return consultaMapper.parseDadosConsulta(consulta);

    }

    private Optional<Medico> getMedicoOuProcurarPorId(Medico medico, Long medicoId) {

        if (!Objects.equals(medico.getId(), medicoId)) {

            return medicoService.pesquisarPorIdAndUsuarioAtivo(medicoId, Medico.class);

        }

        return Boolean.TRUE.equals(medico.getUsuario().getAtivo())
                ? Optional.of(medico)
                : Optional.empty();

    }

    @Transactional
    public void deletarPorId(Long id, Long usuarioId, PerfilUsuario usuarioPerfil) {

        final var consulta = pesquisarPorIdAndUsuarioId(
                id,
                usuarioId,
                usuarioPerfil,
                Consulta.class)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada."));

        if (podeSerCancelada(consulta.getDataHora())) {
            consultaRepository.deleteById(consulta.getId());
        } else {
            throw AgendamentoConsultaException.antecedenciaMinimaCancelamento(MINUTOS_ANTECEDENCIA);
        }

    }

    private boolean podeSerCancelada(OffsetDateTime dataHora) {

        return OffsetDateTime
                .now(Clock.systemDefaultZone())
                .plusMinutes(MINUTOS_ANTECEDENCIA)
                .isBefore(dataHora);

    }

}
