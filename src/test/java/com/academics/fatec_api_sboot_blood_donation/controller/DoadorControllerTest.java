package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo; 
import com.academics.fatec_api_sboot_blood_donation.service.DoadorService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoadorService doadorService;

    @Autowired
    private JacksonTester<DoadorRequest> doadorRequest;

    @Autowired
    private JacksonTester<UpdateDoadorRequest> updateDoadorRequest;

    @Autowired
    private JacksonTester<DoadorResponse> doadorResponse;

    @Autowired
    private JacksonTester<List<DoadorResponse>> listDoadorResponse;

    @Test
    @DisplayName("Deve retornar status 201 e o doador criado ao cadastrar com sucesso")
    void cadastrarDoadorCenario1() throws Exception {
        DoadorRequest request = new DoadorRequest(
                "teste",
                "teste",
                'F',
                LocalDate.of(2000, 2, 2),
                TipoSanguineo.AB_NEGATIVO,
                "teste@teste.com",
                "11111111111"
        );

        Doador doadorSalvo = new Doador(request);
        doadorSalvo.setId(1);
        when(doadorService.cadastrarDoador(any(DoadorRequest.class))).thenReturn(doadorSalvo);

        MockHttpServletResponse response = mockMvc.perform(
                post("/doador")
                        .content(doadorRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String jsonEsperado = doadorResponse.write(new DoadorResponse(doadorSalvo)).getJson();

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getHeader("Location")).endsWith("/doador/1");
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar status 200 e a lista de doadores para o tipo sanguíneo")
    void pesquisarPorTipoSanguineo_Cenario1() throws Exception {
        TipoSanguineo tipoSanguineo = TipoSanguineo.AB_NEGATIVO;
        var doador1 = new Doador(1, "Doador", "1", 'F', LocalDate.of(1990, 1, 1), tipoSanguineo, null, true, "email1@teste.com", "11111111111", null);
        var doador2 = new Doador(2, "Doador", "2", 'M', LocalDate.of(1985, 5, 5), tipoSanguineo, null, true, "email2@teste.com", "22222222222", null);
        List<Doador> doadores = List.of(doador1, doador2);

        when(doadorService.pesquisarPorTipoSanguineo(tipoSanguineo)).thenReturn(doadores);

        List<DoadorResponse> doadorResponses = doadores.stream().map(DoadorResponse::new).toList();

        String jsonEsperado = listDoadorResponse.write(doadorResponses).getJson();

        MockHttpServletResponse response = mockMvc.perform(
                get("/doador")
                        .param("tipoSanguineo", String.valueOf(tipoSanguineo))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar status 204 ao desativar um doador com sucesso")
    void desativarDoador_Cenario1() throws Exception {
        int doadorId = 1;
        doNothing().when(doadorService).desativarDoador(doadorId);

        MockHttpServletResponse response = mockMvc.perform(
                delete("/doador/{id}", doadorId)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(204);
        verify(doadorService, times(1)).desativarDoador(doadorId);
    }

    @Test
    @DisplayName("Deve retornar status 200 e o doador atualizado")
    void atualizarDoador_Cenario1() throws Exception {
        int doadorId = 1;
        UpdateDoadorRequest request = new UpdateDoadorRequest(
                doadorId,
                'M', 
                true,
                "novoemail@teste.com", 
                "11987654321"
        );
        
        Doador doadorAtualizado = new Doador();
        doadorAtualizado.setId(doadorId);
        doadorAtualizado.setNome("Nome Original");
        doadorAtualizado.setSobrenome("Sobrenome Original");
        doadorAtualizado.setGenero(request.genero());
        doadorAtualizado.setEmail(request.email()); 
        doadorAtualizado.setTelefone(request.telefone());
        doadorAtualizado.setAtivo(request.ativo());
        doadorAtualizado.setDataNascimento(LocalDate.of(1995, 1, 1));
        doadorAtualizado.setTipoSanguineo(TipoSanguineo.O_POSITIVO);

        when(doadorService.atualizarDoador(any(UpdateDoadorRequest.class))).thenReturn(doadorAtualizado);

        MockHttpServletResponse response = mockMvc.perform(
                put("/doador")
                        .content(updateDoadorRequest.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String jsonEsperado = doadorResponse.write(new DoadorResponse(doadorAtualizado)).getJson();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
        verify(doadorService, times(1)).atualizarDoador(request);
    }
}