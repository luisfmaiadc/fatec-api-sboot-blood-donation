package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoResponse;
import com.academics.fatec_api_sboot_blood_donation.service.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/doacao")
public class DoacaoController {

    @Autowired
    private DoacaoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DoacaoResponse> cadastrarDoacao(@RequestBody @Valid DoacaoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Doacao doacao = service.cadastrarDoacao(request);
        DoacaoResponse response = new DoacaoResponse(doacao);
        URI uri = uriComponentsBuilder.path("/doacao/{id}").buildAndExpand(doacao.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}