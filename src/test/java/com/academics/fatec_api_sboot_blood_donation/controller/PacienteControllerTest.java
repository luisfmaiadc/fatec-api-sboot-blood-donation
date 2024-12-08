package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.service.PacienteService;
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
import java.time.LocalDate;

import static com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo.A_POSITIVO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PacienteService pacienteService;

    @Autowired
    private JacksonTester<PacienteRequest> pacienteRequest;

    @Autowired
    private JacksonTester<UpdatePacienteRequest> updatePacienteRequest;

    @Test
    void cadastrarPaciente() throws Exception {
        PacienteRequest request = new PacienteRequest(
                "teste",
                "teste",
                "M",
                LocalDate.of(2000, 2,2),
                A_POSITIVO,
                "teste@teste.com",
                "11111111111"
        );

        when(pacienteService.cadastrarPaciente(any(PacienteRequest.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(URI.create("/paciente/1")).build());

        MockHttpServletResponse response = mockMvc.perform(
                post("/paciente")
                        .content(pacienteRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/paciente/1", response.getHeader("Location"));
    }

    @Test
    void cadastrarPacienteErro400() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                post("/paciente")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void pesquisarPacientes() throws Exception {
        when(pacienteService.pesquisarPacientes()).thenReturn(ResponseEntity.ok().build());

        MockHttpServletResponse response = mockMvc.perform(
                get("/paciente")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void atualizarPaciente() throws Exception {
        UpdatePacienteRequest request = new UpdatePacienteRequest(
                1,
                "teste",
                null,
                null,
                null,
                null,
                null,
                null
        );

        when(pacienteService.atualizarPaciente(any(UpdatePacienteRequest.class))).thenReturn(ResponseEntity.ok(request));

        MockHttpServletResponse response = mockMvc.perform(
                put("/paciente")
                        .content(updatePacienteRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }
}