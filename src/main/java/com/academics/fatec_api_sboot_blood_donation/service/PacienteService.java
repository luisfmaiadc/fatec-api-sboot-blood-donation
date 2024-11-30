package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteResponse;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public ResponseEntity cadastrarPaciente(PacienteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = new Paciente(request);
        pacienteRepository.save(paciente);
        URI uri = uriComponentsBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteResponse(paciente));
    }

    public ResponseEntity atualizarPaciente(@Valid UpdatePacienteRequest request) {
        Paciente paciente = pacienteRepository.getReferenceById(request.idPaciente());
        paciente.atualizarPaciente(request);
        pacienteRepository.save(paciente);
        return ResponseEntity.ok(new PacienteResponse(paciente));
    }

    public ResponseEntity pesquisarPacientes() {
        var pacientesList = pacienteRepository.findAll();
        return ResponseEntity.ok(pacientesList.stream().map(PacienteResponse::new).toList());
    }
}
