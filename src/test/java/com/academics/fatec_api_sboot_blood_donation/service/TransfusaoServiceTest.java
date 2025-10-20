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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    private TransfusaoRepository transfusaoRepository;

    @Test
    @DisplayName("Deve cadastrar uma transfusão com sucesso quando os tipos sanguíneos são compatíveis")
    void cadastrarTransfusaoCenario1() {
        var request = new TransfusaoRequest(1, 2, 3);

        var paciente = new Paciente();
        paciente.setId(request.idPaciente());
        paciente.setTipoSanguineo(TipoSanguineo.A_POSITIVO);

        var doador = new Doador();
        doador.setTipoSanguineo(TipoSanguineo.O_NEGATIVO);

        var doacao = new Doacao();
        doacao.setId(request.idDoacao());
        doacao.setDoador(doador);

        var enfermeiro = new Enfermeiro();
        enfermeiro.setId(request.idEnfermeiro());

        when(pacienteRepository.getReferenceById(request.idPaciente())).thenReturn(paciente);
        when(doacaoRepository.getReferenceById(request.idDoacao())).thenReturn(doacao);
        when(enfermeiroRepository.getReferenceById(request.idEnfermeiro())).thenReturn(enfermeiro);
        when(transfusaoRepository.save(any(Transfusao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transfusao transfusaoSalva = transfusaoService.cadastrarTranfusao(request);

        verify(transfusaoRepository).save(any(Transfusao.class));
        assertThat(transfusaoSalva).isNotNull();
        assertThat(transfusaoSalva.getPaciente()).isEqualTo(paciente);
        assertThat(transfusaoSalva.getDoacao()).isEqualTo(doacao);
        assertThat(transfusaoSalva.getEnfermeiro()).isEqualTo(enfermeiro);
    }

    @Test
    @DisplayName("Deve lançar IncompatibleBloodTypeException quando os tipos sanguíneos são incompatíveis")
    void cadastrarTransfusaoCenario2() {
        var request = new TransfusaoRequest(1, 2, 3);

        var paciente = new Paciente();
        paciente.setTipoSanguineo(TipoSanguineo.O_NEGATIVO);

        var doador = new Doador();
        doador.setTipoSanguineo(TipoSanguineo.AB_POSITIVO);

        var doacao = new Doacao();
        doacao.setDoador(doador);

        when(pacienteRepository.getReferenceById(request.idPaciente())).thenReturn(paciente);
        when(doacaoRepository.getReferenceById(request.idDoacao())).thenReturn(doacao);

        assertThatThrownBy(() -> transfusaoService.cadastrarTranfusao(request))
                .isInstanceOf(IncompatibleBloodTypeException.class)
                .hasMessage("Tipo sanguíneo incompatível!");

        verify(transfusaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve repassar EntityNotFoundException se o paciente não existir")
    void cadastrarTransfusaoCenario3() {
        var request = new TransfusaoRequest(999, 2, 3);

        when(pacienteRepository.getReferenceById(request.idPaciente())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> transfusaoService.cadastrarTranfusao(request))
                .isInstanceOf(EntityNotFoundException.class);

        verify(doacaoRepository, never()).getReferenceById(any());
        verify(enfermeiroRepository, never()).getReferenceById(any());
        verify(transfusaoRepository, never()).save(any());
    }
}