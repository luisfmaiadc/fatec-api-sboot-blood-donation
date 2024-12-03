package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.service.DoacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    private JacksonTester<DoacaoRequest> dto;

    @Test
    void cadastrarDoacao() throws Exception {
        DoacaoRequest request = new DoacaoRequest(1, 1);
        given(doacaoService.cadastrarDoacao(any(DoacaoRequest.class), any(UriComponentsBuilder.class)))
                .willReturn(ResponseEntity.created(URI.create("/doacao/1")).build());

        MockHttpServletResponse response = mockMvc.perform(
                post("/doacao")
                        .content(dto.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/doacao/1", response.getHeader("Location"));
    }

    @Test
    void cadastrarDoacaoErro400() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                post("/doacao")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

}