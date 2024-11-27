package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.DoacaoRequest;
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

@RestController
@RequestMapping("/doacao")
public class DoacaoController {

    @Autowired
    private DoacaoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarDoacao(@RequestBody @Valid DoacaoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        return service.cadastrarDoacao(request, uriComponentsBuilder);
    }
}