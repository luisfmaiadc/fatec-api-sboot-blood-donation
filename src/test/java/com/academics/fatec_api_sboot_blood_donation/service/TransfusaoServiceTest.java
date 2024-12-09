package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.Transfusao;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoRequest;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.IncompatibleBloodTypeException;
import com.academics.fatec_api_sboot_blood_donation.repository.DoacaoRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.EnfermeiroRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.PacienteRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.TransfusaoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransfusaoServiceTest {

    @InjectMocks
    private TransfusaoService transfusaoService;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;

    @Mock
    private Paciente paciente;

    @Mock
    private Doacao doacao;

    @Mock
    private Enfermeiro enfermeiro;

    @Mock
    private TransfusaoRepository transfusaoRepository;

    @Captor
    private ArgumentCaptor<Transfusao> transfusaoArgumentCaptor;

    @Mock
    private Doador doador;

    @Test
    void cadastrarTransfusao() {
        TransfusaoRequest request = new TransfusaoRequest(1, 1, 1);

        when(pacienteRepository.getReferenceById(request.idPaciente())).thenReturn(paciente);
        when(doacaoRepository.getReferenceById(request.idDoacao())).thenReturn(doacao);
        when(enfermeiroRepository.getReferenceById(request.idEnfermeiro())).thenReturn(enfermeiro);
        when(paciente.getTipoSanguineo()).thenReturn(TipoSanguineo.A_NEGATIVO);
        when(doacao.getDoador()).thenReturn(doador);
        when(doador.getTipoSanguineo()).thenReturn(TipoSanguineo.A_NEGATIVO);

        Transfusao transfusao = new Transfusao(paciente, doacao, enfermeiro);
        when(transfusaoRepository.save(transfusaoArgumentCaptor.capture())).thenReturn(transfusao);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        ResponseEntity response = transfusaoService.cadastrarTranfusao(request, uriBuilder);
        then(transfusaoRepository).should().save(transfusaoArgumentCaptor.capture());
        Transfusao transfusaoCaptured = transfusaoArgumentCaptor.getValue();

        assertEquals(transfusao.getDoacao().getDoador().getTipoSanguineo(), transfusaoCaptured.getDoacao().getDoador().getTipoSanguineo());
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
    }

    @Test
    @DisplayName("Verifica se não lança Exception se o tipo sanguinéo for compatível.")
    void verificarTipoSanguineoCorreto() {
        assertDoesNotThrow(() -> transfusaoService.verificarTipoSanguineo("A-", "A+"));
    }

    @Test
    @DisplayName("Verifica se lança Exception se o tipo sanguinéo for incompatível.")
    void verificarTipoSanguineoIncorreto() {
        assertThrows(IncompatibleBloodTypeException.class, () -> transfusaoService.verificarTipoSanguineo("AB+", "0-"));
    }

}
