package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.service.DoacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoacaoService doacaoService;

    @Autowired
    private JacksonTester<DoacaoRequest> doacaoRequestJson;

    @Autowired
    private JacksonTester<DoacaoResponse> doacaoResponseJson;

    @Test
    @DisplayName("Deve retornar status 201 e os dados da doação ao cadastrar com sucesso")
    void cadastrarDoacaoCenario1() throws Exception {
        var request = new DoacaoRequest(1, 2);

        var doador = new Doador();
        doador.setId(1);
        doador.setNome("Doador Teste");

        var enfermeiro = new Enfermeiro();
        enfermeiro.setId(2);
        enfermeiro.setNome("Enfermeiro Teste");

        var doacaoSalva = new Doacao(1, doador, enfermeiro, LocalDateTime.now());
        when(doacaoService.cadastrarDoacao(any(DoacaoRequest.class))).thenReturn(doacaoSalva);

        MockHttpServletResponse response = mockMvc.perform(
                post("/doacao")
                        .content(doacaoRequestJson.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        var jsonEsperado = doacaoResponseJson.write(new DoacaoResponse(doacaoSalva)).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).endsWith("/doacao/1");
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar status 400 ao tentar cadastrar com dados inválidos")
    void cadastrarDoacaoErro400() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                post("/doacao")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}