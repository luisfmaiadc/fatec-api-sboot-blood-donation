package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.service.DoadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/doador")
public class DoadorController {

    @Autowired
    private DoadorService doadorService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarDoador(@RequestBody @Valid DoadorRequest request, UriComponentsBuilder uriComponentsBuilder) {
        return doadorService.cadastrarDoador(request, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<List<DoadorResponse>> pesquisarPorTipoSanguineo(@RequestParam TipoSanguineo tipoSanguineo) {
        return doadorService.pesquisarPorTipoSanguineo(tipoSanguineo);
    }
}
