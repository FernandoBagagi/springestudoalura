package br.com.ferdbgg.springestudoalura.repository.specification;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import br.com.ferdbgg.springestudoalura.domain.entity.Consulta;
import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosFiltroConsulta;

public final class ConsultaSpecifications {

    private static final String ID = "id";
    private static final String DIA = "dia";
    private static final String HORA = "hora";
    private static final String MEDICO = "medico";
    private static final String NOME = "nome";
    private static final String ESPECIALIDADE = "especialidade";
    private static final String CRM = "crm";
    private static final String PACIENTE = "paciente";

    private ConsultaSpecifications() {
    }

    private Specification<Consulta> idEquals(Long id) {
        return (root, query, cb) -> id == null
                ? null
                : cb.equal(root.get(ID), id);
    }

    private Specification<Consulta> diaEquals(LocalDate dia) {
        return (root, query, cb) -> dia == null
                ? null
                : cb.equal(root.get(DIA), dia);
    }

    private Specification<Consulta> diaBetween(LocalDate inicio, LocalDate fim) {

        return (root, query, cb) -> {

            if (inicio == null) {

                if (fim == null) {
                    return null;
                }

                return cb.lessThanOrEqualTo(root.get(DIA), fim);

            } else {

                if (fim == null) {
                    return cb.greaterThanOrEqualTo(root.get(DIA), inicio);
                }

                return cb.between(root.get(DIA), inicio, fim);
            }

        };

    }

    private Specification<Consulta> horaEquals(LocalTime hora) {
        return (root, query, cb) -> hora == null
                ? null
                : cb.equal(root.get(HORA), hora);
    }

    private Specification<Consulta> horaBetween(LocalTime inicio, LocalTime fim) {

        return (root, query, cb) -> {

            if (inicio == null) {

                if (fim == null) {
                    return null;
                }

                return cb.lessThanOrEqualTo(root.get(HORA), fim);

            } else {

                if (fim == null) {
                    return cb.greaterThanOrEqualTo(root.get(HORA), inicio);
                }

                return cb.between(root.get(HORA), inicio, fim);
            }

        };

    }

    private Specification<Consulta> medicoIdEquals(Long medicoId) {
        return (root, query, cb) -> medicoId == null
                ? null
                : cb.equal(root.get(MEDICO).get(ID), medicoId);
    }

    private Specification<Consulta> medicoNomeLike(String nome) {
        return (root, query, cb) -> (nome == null || nome.isBlank())
                ? null
                : cb.like(cb.lower(root.get(MEDICO).get(NOME)), "%" + nome.toLowerCase() + "%");
    }

    private Specification<Consulta> medicoEspecialidadeEquals(EspecialidadeMedico especialidade) {
        return (root, query, cb) -> especialidade == null
                ? null
                : cb.equal(root.get(MEDICO).get(ESPECIALIDADE), especialidade);
    }

    private Specification<Consulta> medicoCrmLike(String crm) {
        return (root, query, cb) -> (crm == null || crm.isBlank())
                ? null
                : cb.like(cb.lower(root.get(MEDICO).get(CRM)), "%" + crm.toLowerCase() + "%");

    }

    private Specification<Consulta> pacienteIdEquals(Long pacienteId) {
        return (root, query, cb) -> pacienteId == null
                ? null
                : cb.equal(root.get(PACIENTE).get(ID), pacienteId);
    }

    private Specification<Consulta> pacienteNomeLike(String nome) {
        return (root, query, cb) -> (nome == null || nome.isBlank())
                ? null
                : cb.like(cb.lower(root.get(PACIENTE).get(NOME)), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Consulta> buildSpecifications(DadosFiltroConsulta filtro) {
        final ConsultaSpecifications specs = new ConsultaSpecifications();
        return Specification
                .where(specs.idEquals(filtro.id()))
                .and(specs.diaEquals(filtro.dia()))
                .and(specs.diaBetween(filtro.diaInicio(), filtro.diaFim()))
                .and(specs.horaEquals(filtro.hora()))
                .and(specs.horaBetween(filtro.horaInicio(), filtro.horaFim()))
                .and(specs.medicoIdEquals(filtro.medicoId()))
                .and(specs.medicoNomeLike(filtro.medicoNome()))
                .and(specs.medicoEspecialidadeEquals(EspecialidadeMedico.parse(filtro.medicoEspecialidade())))
                .and(specs.medicoCrmLike(filtro.medicoCrm()))
                .and(specs.pacienteIdEquals(filtro.pacienteId()))
                .and(specs.pacienteNomeLike(filtro.pacienteNome()));
    }

}
