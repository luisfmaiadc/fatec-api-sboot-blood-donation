package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo.A_POSITIVO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @InjectMocks
    private PacienteService pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    @Captor
    private ArgumentCaptor<Paciente> pacienteArgumentCaptor;

    @Mock
    private Paciente paciente;

    @Mock
    private List<Paciente> pacienteList;

    @Test
    void cadastrarPaciente() {
        PacienteRequest request = new PacienteRequest(
                "teste",
                "teste",
                "M",
                LocalDate.of(2000, 2,2),
                A_POSITIVO,
                "teste@teste.com",
                "11111111111"
        );

        Paciente paciente = new Paciente(request);

        when(pacienteRepository.save(pacienteArgumentCaptor.capture())).thenReturn(paciente);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        ResponseEntity response = pacienteService.cadastrarPaciente(request, uriBuilder);
        then(pacienteRepository).should().save(pacienteArgumentCaptor.capture());
        Paciente capturedPaciente = pacienteArgumentCaptor.getValue();

        assertEquals(paciente.getTipoSanguineo(), capturedPaciente.getTipoSanguineo());
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
    }

    @Test
    void atualizarPaciente() {
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

        paciente.setNome("teste");
        paciente.setTipoSanguineo(A_POSITIVO);
        when(pacienteRepository.getReferenceById(request.idPaciente())).thenReturn(paciente);
        when(pacienteRepository.save(pacienteArgumentCaptor.capture())).thenReturn(paciente);

        ResponseEntity response = pacienteService.atualizarPaciente(request);
        then(pacienteRepository).should().save(pacienteArgumentCaptor.capture());
        Paciente capturedPaciente = pacienteArgumentCaptor.getValue();

        assertEquals(paciente.getNome(), capturedPaciente.getNome());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void pesquisarPacientes() {
        when(pacienteRepository.findAll()).thenReturn(pacienteList);
        ResponseEntity response = pacienteService.pesquisarPacientes();

        assertNotNull(response.getBody());
        assertEquals(pacienteList.size(), ((List<?>) response.getBody()).size());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}