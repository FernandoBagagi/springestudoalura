package br.com.ferdbgg.springestudoalura.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import br.com.ferdbgg.springestudoalura.model.entity.Consulta;
import br.com.ferdbgg.springestudoalura.model.entity.Medico;
import br.com.ferdbgg.springestudoalura.model.entity.Paciente;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Test
    @DisplayName("Deve encontrar e marcar consulta para os médicos de uma especialidade e deve não estar presente quando nenhum médico estiver mais disponível")
    void testFindFirstMedicoDisponivelMedicoIndisponivel() {

        final var especialidade = EspecialidadeMedico.CARDIOLOGIA;
        final var dia = LocalDate.of(2026, 03, 23);
        final var hora = LocalTime.of(14, 00);

        final var totalMedicosDisponiveisEspecialidade = medicoRepository
                .countByEspecialidadeAndUsuarioAtivoTrue(especialidade);

        Optional<Medico> medicoDisponivel;

        for (long i = 1L; i <= totalMedicosDisponiveisEspecialidade; i++) {

            medicoDisponivel = medicoRepository
                    .findFirstMedicoDisponivel(especialidade, dia, hora);

            assertThat(medicoDisponivel).isPresent();

            final var paciente = pacienteRepository
                    .findByIdAndUsuarioAtivo(i, Boolean.TRUE, Paciente.class)
                    .get();

            final var consulta = new Consulta();
            consulta.setMedico(medicoDisponivel.get());
            consulta.setPaciente(paciente);
            consulta.setDia(dia);
            consulta.setHora(hora);

            consultaRepository.save(consulta);

        }

        medicoDisponivel = medicoRepository
                .findFirstMedicoDisponivel(especialidade, dia, hora);

        assertThat(medicoDisponivel).isNotPresent();

    }

}
