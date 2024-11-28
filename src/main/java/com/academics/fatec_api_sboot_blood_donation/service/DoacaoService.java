package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.repository.DoacaoRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.EnfermeiroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private EnfermeiroRepository enfermeiroRepository;

    public ResponseEntity cadastrarDoacao(@Valid DoacaoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Doador doador = doadorRepository.getReferenceById(request.idDoador());
        Enfermeiro enfermeiro = enfermeiroRepository.getReferenceById(request.idEnfermeiro());
        Doacao doacao = new Doacao(doador, enfermeiro);
        doador.setUltimaDoacao(LocalDate.now());
        doacaoRepository.save(doacao);
        URI uri = uriComponentsBuilder.path("/doacao/{id}").buildAndExpand(doacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new DoacaoResponse(doacao));
    }
}
