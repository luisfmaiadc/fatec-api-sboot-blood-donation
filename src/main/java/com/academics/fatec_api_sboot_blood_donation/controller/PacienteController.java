package com.academics.fatec_api_sboot_blood_donation.controller;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid PacienteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        return pacienteService.cadastrarPaciente(request, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity pesquisarPacientes() {
        return pacienteService.pesquisarPacientes();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarPaciente(@RequestBody @Valid UpdatePacienteRequest request) {
        return pacienteService.atualizarPaciente(request);
    }
}
