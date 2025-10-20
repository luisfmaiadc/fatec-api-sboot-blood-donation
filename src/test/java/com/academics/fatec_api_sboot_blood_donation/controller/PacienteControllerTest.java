package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.service.PacienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo.A_POSITIVO;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Autowired
    private JacksonTester<PacienteResponse> pacienteResponse;

    @Autowired
    private JacksonTester<List<PacienteResponse>> listPacienteResponse;

    @Test
    @DisplayName("Deve retornar status 201 e o paciente criado ao cadastrar com sucesso")
    void cadastrarPacienteCenario1() throws Exception {
        PacienteRequest request = new PacienteRequest(
                "teste",
                "teste",
                'M',
                LocalDate.of(2000, 2, 2),
                A_POSITIVO,
                "teste@teste.com",
                "11111111111"
        );

        Paciente pacienteSalvo = new Paciente(request);
        pacienteSalvo.setId(1);
        when(pacienteService.cadastrarPaciente(any(PacienteRequest.class))).thenReturn(pacienteSalvo);

        MockHttpServletResponse response = mockMvc.perform(
                post("/paciente")
                        .content(pacienteRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String jsonEsperado = pacienteResponse.write(new PacienteResponse(pacienteSalvo)).getJson();

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getHeader("Location")).endsWith("/paciente/1");
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar status 400 ao tentar cadastrar com dados inválidos")
    void cadastrarPacienteErro400() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                post("/paciente")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    @DisplayName("Deve retornar status 200 e a lista de pacientes")
    void pesquisarPacientes() throws Exception {
        Paciente paciente = new Paciente(1, "Jorge", "Mateus", 'M', LocalDate.of(1982, 8, 27), A_POSITIVO, "jorge@sertanejo.com.br", "62987654323", null);
        List<Paciente> pacientes = Collections.singletonList(paciente);
        when(pacienteService.pesquisarPacientes()).thenReturn(pacientes);

        MockHttpServletResponse response = mockMvc.perform(
                get("/paciente")
        ).andReturn().getResponse();

        String jsonEsperado = listPacienteResponse.write(
                pacientes.stream().map(PacienteResponse::new).toList()
        ).getJson();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar status 200 e o paciente atualizado")
    void atualizarPacienteCenario1() throws Exception {
        UpdatePacienteRequest request = new UpdatePacienteRequest(
                1,
                'F',
                "novo.email@teste.com",
                "11999998888"
        );

        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado.setId(request.idPaciente());
        pacienteAtualizado.setNome("Jorge");
        pacienteAtualizado.setSobrenome("Mateus");
        pacienteAtualizado.setGenero(request.genero());
        pacienteAtualizado.setEmail(request.email());
        pacienteAtualizado.setTelefone(request.telefone());

        when(pacienteService.atualizarPaciente(any(UpdatePacienteRequest.class))).thenReturn(pacienteAtualizado);

        MockHttpServletResponse response = mockMvc.perform(
                put("/paciente")
                        .content(updatePacienteRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String jsonEsperado = pacienteResponse.write(new PacienteResponse(pacienteAtualizado)).getJson();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}