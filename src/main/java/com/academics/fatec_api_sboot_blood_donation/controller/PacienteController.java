package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<PacienteResponse> cadastrarPaciente(@RequestBody @Valid PacienteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteService.cadastrarPaciente(request);
        PacienteResponse response = new PacienteResponse(paciente);
        URI uri = uriComponentsBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PacienteResponse> atualizarPaciente(@RequestBody @Valid UpdatePacienteRequest request) {
        Paciente paciente = pacienteService.atualizarPaciente(request);
        PacienteResponse response = new PacienteResponse(paciente);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> pesquisarPacientes() {
        List<Paciente> pacienteList = pacienteService.pesquisarPacientes();
        List<PacienteResponse> responseList = pacienteList.stream().map(PacienteResponse::new).toList();
        return ResponseEntity.ok().body(responseList);
    }
}