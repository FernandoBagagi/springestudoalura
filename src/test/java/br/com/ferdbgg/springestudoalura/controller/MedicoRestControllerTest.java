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

import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import br.com.ferdbgg.springestudoalura.model.mapper.MedicoMapper;
import br.com.ferdbgg.springestudoalura.service.MedicoService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoRestControllerTest {

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
                "beatriz.souza@estudo.spring", 
                "beatriz.souza", 
                Genero.FEMININO, 
                "Beatriz", 
                "Souza", 
                "CRM/UF 482391", 
                EspecialidadeMedico.CARDIOLOGIA);

        when(service.cadastrar(any()))
                .thenReturn(mapper.parseDadosBasicos(mapper.parseMedico(dadosCadastro)));

        var response = mvc
                .perform(post("/api/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosBasicos = new DadosBasicosMedico(
                null, 
                dadosCadastro.especialidade(), 
                dadosCadastro.genero(), 
                dadosCadastro.nome(), 
                dadosCadastro.sobrenome(), 
                dadosCadastro.crm());

        var jsonEsperado = dadosBasicosMedicoJson.write(dadosBasicos).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

}
