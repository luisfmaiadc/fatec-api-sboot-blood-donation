package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.InactiveDonor;
import com.academics.fatec_api_sboot_blood_donation.repository.DoacaoRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.EnfermeiroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoacaoServiceTest {

    @InjectMocks
    private DoacaoService doacaoService;

    @Mock
    private DoadorRepository doadorRepository;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;

    @Mock
    private DoacaoRepository doacaoRepository;

    @Test
    @DisplayName("Deve cadastrar uma doação com sucesso para um doador ativo")
    void cadastrarDoacaoCenario1() {
        var request = new DoacaoRequest(1, 1);
        var doador = new Doador();
        doador.setId(request.idDoador());
        doador.setAtivo(true);

        var enfermeiro = new Enfermeiro();
        enfermeiro.setId(request.idEnfermeiro());

        when(doadorRepository.getReferenceById(request.idDoador())).thenReturn(doador);
        when(enfermeiroRepository.getReferenceById(request.idEnfermeiro())).thenReturn(enfermeiro);
        when(doacaoRepository.save(any(Doacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Doacao doacaoSalva = doacaoService.cadastrarDoacao(request);

        verify(doacaoRepository).save(any(Doacao.class));
        assertThat(doacaoSalva).isNotNull();
        assertThat(doacaoSalva.getDoador()).isEqualTo(doador);
        assertThat(doacaoSalva.getEnfermeiro()).isEqualTo(enfermeiro);
        assertThat(doador.getUltimaDoacao()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Deve lançar InactiveDonor ao tentar cadastrar doação para doador inativo")
    void cadastrarDoacaoCenario2() {
        var request = new DoacaoRequest(1, 1);
        var doadorInativo = new Doador();
        doadorInativo.setId(request.idDoador());
        doadorInativo.setAtivo(false);

        when(doadorRepository.getReferenceById(request.idDoador())).thenReturn(doadorInativo);

        assertThatThrownBy(() -> doacaoService.cadastrarDoacao(request))
                .isInstanceOf(InactiveDonor.class)
                .hasMessage("Cadastro de doador informado não está disponível para novas doações.");

        verify(enfermeiroRepository, never()).getReferenceById(any());
        verify(doacaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve repassar EntityNotFoundException se o doador não existir")
    void cadastrarDoacaoCenario3() {
        var request = new DoacaoRequest(999, 1);
        when(doadorRepository.getReferenceById(request.idDoador())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> doacaoService.cadastrarDoacao(request))
                .isInstanceOf(EntityNotFoundException.class);
    }
}