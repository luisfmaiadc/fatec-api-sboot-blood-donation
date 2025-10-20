package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo.A_POSITIVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @InjectMocks
    private PacienteService pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Deve cadastrar um paciente com sucesso")
    void cadastrarPacienteCenario1() {
        var request = new PacienteRequest(
                "teste",
                "teste",
                'M',
                LocalDate.of(2000, 2, 2),
                A_POSITIVO,
                "teste@teste.com",
                "11111111111"
        );

        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Paciente pacienteSalvo = pacienteService.cadastrarPaciente(request);

        then(pacienteRepository).should().save(any(Paciente.class));
        assertThat(pacienteSalvo).isNotNull();
        assertThat(pacienteSalvo.getNome()).isEqualTo(request.nome());
        assertThat(pacienteSalvo.getEmail()).isEqualTo(request.email());
    }

    @Test
    @DisplayName("Deve atualizar os dados de um paciente com sucesso")
    void atualizarPacienteCenario1() {
        Integer pacienteId = 1;
        var request = new UpdatePacienteRequest(
                pacienteId,
                'F',
                "novo.email@teste.com",
                "11999998888"
        );

        var pacienteOriginal = new Paciente(pacienteId, "Nome", "Original", 'M', LocalDate.now().minusYears(30), A_POSITIVO, "original@email.com", "55555555555", null);
        when(pacienteRepository.getReferenceById(pacienteId)).thenReturn(pacienteOriginal);

        Paciente pacienteAtualizado = pacienteService.atualizarPaciente(request);

        then(pacienteRepository).should().save(pacienteOriginal);
        assertThat(pacienteAtualizado.getNome()).isEqualTo("Nome");
        assertThat(pacienteAtualizado.getSobrenome()).isEqualTo("Original");
        assertThat(pacienteAtualizado.getGenero()).isEqualTo('F');
        assertThat(pacienteAtualizado.getEmail()).isEqualTo("novo.email@teste.com");
        assertThat(pacienteAtualizado.getTelefone()).isEqualTo("11999998888");
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar atualizar paciente inexistente")
    void atualizarPacienteCenario2() {
        final Integer pacienteIdInexistente = 999;
        var request = new UpdatePacienteRequest(pacienteIdInexistente, 'F', "email@novo.com",  "11999998877");

        when(pacienteRepository.getReferenceById(pacienteIdInexistente)).thenThrow(new EntityNotFoundException("Unable to find com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente with id " + pacienteIdInexistente));

        assertThatThrownBy(() -> pacienteService.atualizarPaciente(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Paciente não encontrado com o ID: " + pacienteIdInexistente);
    }

    @Test
    @DisplayName("Deve retornar uma lista de pacientes")
    void pesquisarPacientesCenario1() {
        var paciente1 = new Paciente(1, "Jorge", "Mateus", 'M', LocalDate.of(1982, 8, 27), A_POSITIVO, "jorge@sertanejo.com.br", "62987654323", null);
        List<Paciente> pacientesMock = Collections.singletonList(paciente1);
        when(pacienteRepository.findAll()).thenReturn(pacientesMock);

        List<Paciente> resultado = pacienteService.pesquisarPacientes();

        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getNome()).isEqualTo("Jorge");
    }
}