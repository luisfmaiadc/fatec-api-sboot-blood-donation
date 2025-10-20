package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.InactiveDonor;
import com.academics.fatec_api_sboot_blood_donation.repository.DoacaoRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.EnfermeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private EnfermeiroRepository enfermeiroRepository;

    public Doacao cadastrarDoacao(DoacaoRequest request) {
        Doador doador = doadorRepository.getReferenceById(request.idDoador());
        verificarDoadorAtivo(doador);
        Enfermeiro enfermeiro = enfermeiroRepository.getReferenceById(request.idEnfermeiro());
        Doacao doacao = new Doacao(doador, enfermeiro);
        doador.setUltimaDoacao(LocalDate.now());
        doacaoRepository.save(doacao);
        return doacao;
    }

    private void verificarDoadorAtivo(Doador doador) {
        if (!doador.getAtivo()) {
            throw new InactiveDonor("Cadastro de doador informado não está disponível para novas doações.");
        }
    }
}