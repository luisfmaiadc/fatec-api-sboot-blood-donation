package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.service.DoadorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoadorService doadorService;

    @Autowired
    private JacksonTester<DoadorRequest> doadorRequest;

    @Autowired
    private JacksonTester<UpdateDoadorRequest> updateDoadorRequest;

    @Mock
    private Doador doador;

    @Test
    void cadastrarDoador() throws Exception {
        DoadorRequest request = new DoadorRequest(
                "teste",
                "teste",
                "F",
                LocalDate.of(2000, 2, 2),
                TipoSanguineo.AB_NEGATIVO,
                "teste@teste.com",
                "11111111111"
        );

        when(doadorService.cadastrarDoador(any(DoadorRequest.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(URI.create("/doador/1")).build());

        MockHttpServletResponse response = mockMvc.perform(
                post("/doador")
                        .content(doadorRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/doador/1", response.getHeader("Location"));
    }

    @Test
    void pesquisarPorTipoSanguineo() throws Exception {
        TipoSanguineo tipoSanguineo = TipoSanguineo.AB_NEGATIVO;
        List<DoadorResponse> doadores = List.of(
                new DoadorResponse(new Doador(1, "Doador", "1", "F", LocalDate.of(1990, 1, 1), tipoSanguineo, null, true, "email1@teste.com", "11111111111", null)),
                new DoadorResponse(new Doador(2, "Doador", "2", "M", LocalDate.of(1985, 5, 5), tipoSanguineo, null, true, "email2@teste.com", "22222222222", null)) );


        when(doadorService.pesquisarPorTipoSanguineo(tipoSanguineo)).thenReturn(ResponseEntity.ok(doadores));

        MockHttpServletResponse response = mockMvc.perform(
                get("/doador")
                        .param("tipoSanguineo", String.valueOf(tipoSanguineo))
                        .accept(MediaType.APPLICATION_JSON) )
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertNotNull(response.getContentAsString());
    }

    @Test
    void desativarDoador() throws Exception {
        when(doadorService.desativarDoador(1)).thenReturn(ResponseEntity.noContent().build());

        MockHttpServletResponse response = mockMvc.perform(
                delete("/doador/1")
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    @Test
    void atualizarDoador() throws Exception {
        UpdateDoadorRequest request = new UpdateDoadorRequest(
                1,
                null,
                null,
                "M",
                null,
                null,
                null,
                null,
                null
        );

        when(doador.getGenero()).thenReturn("M");

        DoadorResponse doadorResponse = new DoadorResponse(doador);
        when(doadorService.atualizarDoador(any(UpdateDoadorRequest.class))).thenReturn(ResponseEntity.ok(doadorResponse));

        MockHttpServletResponse response = mockMvc.perform(
                put("/doador")
                        .content(updateDoadorRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(request.genero(), doadorResponse.genero());
    }
}