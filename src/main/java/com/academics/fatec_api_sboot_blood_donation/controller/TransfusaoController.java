package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoRequest;
import com.academics.fatec_api_sboot_blood_donation.service.TransfusaoService;
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
@RequestMapping("/transfusao")
public class TransfusaoController {

    @Autowired
    private TransfusaoService transfusaoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTransfusao(@RequestBody @Valid TransfusaoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        return transfusaoService.cadastrarTranfusao(request, uriComponentsBuilder);
    }

}
