package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.Transfusao;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoResponse;
import com.academics.fatec_api_sboot_blood_donation.service.TransfusaoService;
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
class TransfusaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransfusaoService transfusaoService;

    @Autowired
    private JacksonTester<TransfusaoRequest> transfusaoRequestJson;

    @Autowired
    private JacksonTester<TransfusaoResponse> transfusaoResponseJson;

    @Test
    @DisplayName("Deve retornar status 201 e os dados da transfusão ao cadastrar com sucesso")
    void cadastrarTransfusaoCenario1() throws Exception {
        var request = new TransfusaoRequest(1, 2, 3);

        var paciente = new Paciente();
        paciente.setId(1);
        paciente.setNome("Paciente Teste");

        var doacao = new Doacao();
        doacao.setId(2);

        var enfermeiro = new Enfermeiro();
        enfermeiro.setId(3);
        enfermeiro.setNome("Enfermeiro Teste");

        var transfusaoSalva = new Transfusao(1, paciente, doacao, enfermeiro, LocalDateTime.now());
        when(transfusaoService.cadastrarTranfusao(any(TransfusaoRequest.class))).thenReturn(transfusaoSalva);

        MockHttpServletResponse response = mockMvc.perform(
                post("/transfusao")
                        .content(transfusaoRequestJson.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        var jsonEsperado = transfusaoResponseJson.write(new TransfusaoResponse(transfusaoSalva)).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).endsWith("/transfusao/1");
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}