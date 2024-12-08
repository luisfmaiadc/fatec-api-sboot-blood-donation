package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.AgeException;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo.A_POSITIVO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoadorServiceTest {

    @InjectMocks
    private DoadorService doadorService;

    @Mock
    private DoadorRepository doadorRepository;

    @Captor
    private ArgumentCaptor<Doador> doadorArgumentCaptor;

    @Mock
    private Doador doador;

    @Test
    void cadastrarDoador() {
        DoadorRequest request = new DoadorRequest(
                "teste",
                "teste",
                "F",
                LocalDate.of(2000, 2, 2),
                TipoSanguineo.AB_NEGATIVO,
                "teste@teste.com",
                "11111111111"
        );

        Doador doador = new Doador(request);

        when(doadorRepository.save(doadorArgumentCaptor.capture())).thenReturn(doador);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        ResponseEntity response = doadorService.cadastrarDoador(request, uriBuilder);
        then(doadorRepository).should().save(doadorArgumentCaptor.capture());
        Doador capturedDoador = doadorArgumentCaptor.getValue();

        assertEquals(doador.getNomeDoadorCompleto(), capturedDoador.getNomeDoadorCompleto());
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
    }

    @Test
    @DisplayName("Não lança Exception caso o doador tenha 16 anos ou mais.")
    void verificarIdadeCorreta() {
        given(doador.getDataNascimento()).willReturn(LocalDate.now().minusYears(16));
        assertDoesNotThrow(() -> doadorService.verificarIdadeMinima(doador.getDataNascimento()));
    }

    @Test
    @DisplayName("Lança Exception caso o doador tenha menos de 16 anos")
    void verificarIdadeIncorreta() {
        given(doador.getDataNascimento()).willReturn(LocalDate.now().minusYears(15));
        assertThrows(AgeException.class, () -> doadorService.verificarIdadeMinima(doador.getDataNascimento()));
    }

    @Test
    void pesquisarPorTipoSanguineo() {
        TipoSanguineo tipoSanguineo = TipoSanguineo.AB_NEGATIVO;
        List<Doador> doadores = List.of(
                new Doador(1, "Doador", "1", "F", LocalDate.of(1990, 1, 1), tipoSanguineo, null, true, "email1@teste.com", "11111111111", null),
                new Doador(2, "Doador", "2", "M", LocalDate.of(1985, 5, 5), tipoSanguineo, null, true, "email2@teste.com", "22222222222" , null)
        );
        when(doadorRepository.findByTipoSanguineo(tipoSanguineo)).thenReturn(doadores);

        ResponseEntity<List<DoadorResponse>> response = doadorService.pesquisarPorTipoSanguineo(tipoSanguineo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Doador 1", response.getBody().get(0).nomeCompleto());
        assertEquals("Doador 2", response.getBody().get(1).nomeCompleto());
    }

    @Test
    void desativarDoador() {
        doador.setAtivo(true);
        when(doadorRepository.getReferenceById(1)).thenReturn(doador);

        ResponseEntity response = doadorService.desativarDoador(1);

        assertEquals(false, doador.getAtivo());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void atualizarDoador() {
        UpdateDoadorRequest request = new UpdateDoadorRequest(
                1,
                "teste",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        doador.setNome("teste");
        doador.setTipoSanguineo(A_POSITIVO);
        when(doadorRepository.getReferenceById(request.idDoador())).thenReturn(doador);
        when(doadorRepository.save(doadorArgumentCaptor.capture())).thenReturn(doador);

        ResponseEntity response = doadorService.atualizarDoador(request);
        then(doadorRepository).should().save(doadorArgumentCaptor.capture());
        Doador capturedDoador = doadorArgumentCaptor.getValue();

        assertEquals(doador.getNome(), capturedDoador.getNome());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

}