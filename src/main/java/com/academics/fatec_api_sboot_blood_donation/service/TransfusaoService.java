package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.Transfusao;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoRequest;

public interface TransfusaoService {

    Transfusao cadastrarTranfusao(TransfusaoRequest request);
}
