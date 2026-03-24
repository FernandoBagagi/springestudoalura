package br.com.ferdbgg.springestudoalura.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;

    @MockitoBean
    private ConsultaService service;

    @Test
    @DisplayName("Deve devolver código 400 quando informações não forem válidas")
    @WithMockUser
    void testAgendarConsultaDadosInvalidos() throws Exception {

        final MockHttpServletResponse response = mvc
                .perform(post("/consultas"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deve devolver código 200 quando informações forem válidas")
    @WithMockUser
    void testAgendarConsultaDadosValidos() throws Exception {

        final long idMedico = 1L;
        final EspecialidadeMedico especialidade = EspecialidadeMedico.CARDIOLOGIA;
        final long idPaciente = 1L;
        final LocalDate dia = LocalDate.of(2026, 03, 23);
        final LocalTime hora = LocalTime.of(14, 00);
        final OffsetDateTime dataHora = DataHoraUtil
                .converterParaOffsetDateTime(dia, hora);

        final DadosAgendamentoConsulta dadosAgendamentoConsulta = //
                new DadosAgendamentoConsulta(idMedico, especialidade, idPaciente, dataHora);

        final String jsonRequest = dadosAgendamentoConsultaJacksonTester
                .write(dadosAgendamentoConsulta)
                .getJson();

        final MockHttpServletRequestBuilder requestBuilder = post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        final MockHttpServletResponse response = mvc
                .perform(requestBuilder)
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        when(service.agendar(dadosAgendamentoConsulta)).thenReturn(null);

        assertThat(response.getContentAsString()).isNullOrEmpty();

    }

}
