package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.service.DoadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doador")
public class DoadorController {

    @Autowired
    private DoadorService doadorService;

    @PostMapping
    @Transactional
    public ResponseEntity<DoadorResponse> cadastrarDoador(@RequestBody @Valid DoadorRequest request) {
        Doador doador = doadorService.cadastrarDoador(request);
        URI uri = UriComponentsBuilder.fromPath("/doador/{id}").buildAndExpand(doador.getId()).toUri();
        return ResponseEntity.created(uri).body(new DoadorResponse(doador));
    }

    @GetMapping
    public ResponseEntity<List<DoadorResponse>> pesquisarPorTipoSanguineo(@RequestParam TipoSanguineo tipoSanguineo) {
        List<Doador> doadorList = doadorService.pesquisarPorTipoSanguineo(tipoSanguineo);
        List<DoadorResponse> responseList = doadorList.stream().map(DoadorResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> desativarDoador(@PathVariable Integer id) {
         doadorService.desativarDoador(id);
         return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoadorResponse> atualizarDoador(@RequestBody @Valid UpdateDoadorRequest request) {
        Doador doador = doadorService.atualizarDoador(request);
        return ResponseEntity.ok(new DoadorResponse(doador));
    }
}
