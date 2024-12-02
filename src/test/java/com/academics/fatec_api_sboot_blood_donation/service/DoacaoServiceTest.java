package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.InactiveDonor;
import com.academics.fatec_api_sboot_blood_donation.repository.DoacaoRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.EnfermeiroRepository;
import org.junit.jupiter.api.DisplayName;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DoacaoServiceTest {

    @InjectMocks
    private DoacaoService doacaoService;

    @Mock
    private DoadorRepository doadorRepository;

    @Mock
    private Doador doador;

    @Mock
    private Enfermeiro enfermeiro;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;

    @Captor
    private ArgumentCaptor<Doacao> doacaoArgumentCaptor;

    @Mock
    private DoacaoRepository doacaoRepository;

    @Test
    void cadastrarDoacao() {
        // Arrange
        DoacaoRequest request = new DoacaoRequest(1, 1);
        given(doadorRepository.getReferenceById(request.idDoador())).willReturn(doador);
        given(doador.getAtivo()).willReturn(true);
        given(enfermeiroRepository.getReferenceById(request.idEnfermeiro())).willReturn(enfermeiro);

        Doacao doacao = new Doacao(doador, enfermeiro);
        given(doacaoRepository.save(doacaoArgumentCaptor.capture())).willReturn(doacao);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        //Act
        ResponseEntity response = doacaoService.cadastrarDoacao(request, uriBuilder);
        then(doacaoRepository).should().save(doacaoArgumentCaptor.capture());
        Doacao capturedDoacao = doacaoArgumentCaptor.getValue();

        //Assert
        assertEquals(doacao.getDoador(), capturedDoacao.getDoador());
        assertEquals(doacao.getEnfermeiro(), capturedDoacao.getEnfermeiro());
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
    }

    @Test
    @DisplayName("Verifica se não lança Exception se doador está ativo.")
    void verificarDoadorAtivo() {
        given(doador.getAtivo()).willReturn(true);
        assertDoesNotThrow(() -> doacaoService.verificarDoadorAtivo(doador));
    }

    @Test
    @DisplayName("Verifica se lança Exception se doador está inativo.")
    void verificarDoadorAtivoException() {
        given(doador.getAtivo()).willReturn(false);
        assertThrows(InactiveDonor.class, () -> doacaoService.verificarDoadorAtivo(doador));
    }
}