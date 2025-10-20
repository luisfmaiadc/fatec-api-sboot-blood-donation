package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.AgeException;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import com.academics.fatec_api_sboot_blood_donation.service.impl.DoadorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoadorServiceImplTest {

    @InjectMocks
    private DoadorServiceImpl doadorService;

    @Mock
    private DoadorRepository doadorRepository;

    @Test
    @DisplayName("Deve cadastrar um doador com sucesso")
    void cadastrarDoadorCenario1() {
        var request = new DoadorRequest(
                "teste",
                "teste",
                'F',
                LocalDate.of(2000, 2, 2),
                TipoSanguineo.AB_NEGATIVO,
                "teste@teste.com",
                "11111111111"
        );

        when(doadorRepository.save(any(Doador.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Doador doadorSalvo = doadorService.cadastrarDoador(request);

        then(doadorRepository).should().save(any(Doador.class));
        assertThat(doadorSalvo).isNotNull();
        assertThat(doadorSalvo.getNome()).isEqualTo(request.nome());
        assertThat(doadorSalvo.getEmail()).isEqualTo(request.email());
    }

    @Test
    @DisplayName("Deve lançar AgeException ao tentar cadastrar doador com menos de 16 anos")
    void cadastrarDoadorCenario2() {
        var request = new DoadorRequest(
                "Menor", "Idade", 'M',
                LocalDate.now().minusYears(15), // Idade inválida
                TipoSanguineo.O_POSITIVO, "menor@email.com", "33333333333"
        );

        assertThatThrownBy(() -> doadorService.cadastrarDoador(request))
                .isInstanceOf(AgeException.class)
                .hasMessage("Idade insuficiente para se tornar doador.");
    }

    @Test
    @DisplayName("Deve retornar uma lista de doadores ao pesquisar por tipo sanguíneo")
    void pesquisarPorTipoSanguineo() {
        TipoSanguineo tipoSanguineo = TipoSanguineo.AB_NEGATIVO;
        var doador1 = new Doador(1, "Doador", "1", 'F', LocalDate.of(1990, 1, 1), tipoSanguineo, null, true, "email1@teste.com", "11111111111", null);
        var doador2 = new Doador(2, "Doador", "2", 'M', LocalDate.of(1985, 5, 5), tipoSanguineo, null, true, "email2@teste.com", "22222222222", null);
        List<Doador> doadoresMock = List.of(doador1, doador2);
        when(doadorRepository.findByTipoSanguineo(tipoSanguineo)).thenReturn(doadoresMock);

        List<Doador> resultado = doadorService.pesquisarPorTipoSanguineo(tipoSanguineo);

        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(doadoresMock);
    }

    @Test
    @DisplayName("Deve desativar um doador com sucesso")
    void desativarDoador() {
        Integer doadorId = 1;
        var doador = new Doador(doadorId, "Doador", "Ativo", 'M', LocalDate.now().minusYears(20), TipoSanguineo.A_POSITIVO, null, true, "ativo@email.com", "44444444444", null);
        when(doadorRepository.getReferenceById(doadorId)).thenReturn(doador);

        doadorService.desativarDoador(doadorId);

        then(doadorRepository).should().getReferenceById(doadorId);
        assertThat(doador.getAtivo()).isFalse();
    }

    @Test
    @DisplayName("Deve atualizar os dados de um doador com sucesso")
    void atualizarDoador() {
        Integer doadorId = 1;
        var request = new UpdateDoadorRequest(
                doadorId,
                'F',
                false,
                "novo.email@teste.com",
                null
        );

        var doadorOriginal = new Doador(doadorId, "Nome", "Original", 'M', LocalDate.now().minusYears(30), TipoSanguineo.O_POSITIVO, null, true, "original@email.com", "55555555555", null);
        when(doadorRepository.getReferenceById(doadorId)).thenReturn(doadorOriginal);

        Doador doadorAtualizado = doadorService.atualizarDoador(request);

        then(doadorRepository).should().save(doadorOriginal);
        assertThat(doadorAtualizado.getGenero()).isEqualTo(request.genero());
        assertThat(doadorAtualizado.getEmail()).isEqualTo(request.email());
        assertThat(doadorAtualizado.getAtivo()).isEqualTo(request.ativo());
        assertThat(doadorAtualizado.getNome()).isEqualTo("Nome");
        assertThat(doadorAtualizado.getTipoSanguineo()).isEqualTo(TipoSanguineo.O_POSITIVO);
    }
}