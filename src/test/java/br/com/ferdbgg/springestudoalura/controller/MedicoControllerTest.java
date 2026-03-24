package br.com.ferdbgg.springestudoalura.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.mapper.MedicoMapper;
import br.com.ferdbgg.springestudoalura.service.MedicoService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosBasicosMedico> dadosBasicosMedicoJson;

    @MockitoBean
    private MedicoService service;

    @Autowired
    private MedicoMapper mapper;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 201 quando informacoes estao validas")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var dadosCadastro = new DadosCadastroMedico(
                "Ana Beatriz de Albuquerque Souza",
                "ana.souza@estudo.spring",
                "11 77834-5612",
                "CRM/UF 482391",
                EspecialidadeMedico.CARDIOLOGIA,
                dadosEndereco());

        when(service.cadastrar(any()))
                .thenReturn(mapper.parseDadosBasicos(mapper.parseMedico(dadosCadastro)));

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosBasicos = new DadosBasicosMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.crm(),
                dadosCadastro.especialidade());

        var jsonEsperado = dadosBasicosMedicoJson.write(dadosBasicos).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

    private DadosCadastroEndereco dadosEndereco() {
        return new DadosCadastroEndereco(
                "Avenida Paulista",
                "Bela Vista",
                "01311-000",
                "São Paulo",
                "SP",
                "Sala 1203",
                "1578");
    }

}
