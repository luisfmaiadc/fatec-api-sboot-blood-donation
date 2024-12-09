package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoRequest;
import com.academics.fatec_api_sboot_blood_donation.service.TransfusaoService;
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
    private JacksonTester<TransfusaoRequest> transfusaoRequest;

    @Test
    void cadastrarTransfusao() throws Exception {
        TransfusaoRequest request = new TransfusaoRequest(1, 1, 1);

        when(transfusaoService.cadastrarTranfusao(any(TransfusaoRequest.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(URI.create("/transfusao/1")).build());

        MockHttpServletResponse response = mockMvc.perform(
                post("/transfusao")
                        .content(transfusaoRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/transfusao/1", response.getHeader("Location"));
    }

}