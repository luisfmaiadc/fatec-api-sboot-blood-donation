package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;

import java.util.List;

public interface DoadorService {

    Doador cadastrarDoador(DoadorRequest request);
    Doador atualizarDoador(UpdateDoadorRequest request);
    List<Doador> pesquisarPorTipoSanguineo(TipoSanguineo tipoSanguineo);
    void desativarDoador(Integer id);
}