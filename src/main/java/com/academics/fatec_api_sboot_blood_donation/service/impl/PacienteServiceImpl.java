package com.academics.fatec_api_sboot_blood_donation.service.impl;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.repository.PacienteRepository;
import com.academics.fatec_api_sboot_blood_donation.service.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente cadastrarPaciente(PacienteRequest request) {
        Paciente paciente = new Paciente(request);
        pacienteRepository.save(paciente);
        return paciente;
    }

    @Override
    public Paciente atualizarPaciente(@Valid UpdatePacienteRequest request) {
        try {
            Paciente paciente = pacienteRepository.getReferenceById(request.idPaciente());
            atualizarDadosPaciente(request, paciente);
            pacienteRepository.save(paciente);
            return paciente;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Paciente não encontrado com o ID: " + request.idPaciente());
        }
    }

    @Override
    public List<Paciente> pesquisarPacientes() {
        return pacienteRepository.findAll();
    }

    private void atualizarDadosPaciente(UpdatePacienteRequest request, Paciente paciente) {
        if (request.genero() != null) {
            paciente.setGenero(request.genero());
        }
        
        if (request.email() != null) {
            paciente.setEmail(request.email());
        }

        if (request.telefone() != null) {
            paciente.setTelefone(request.telefone());
        }
    }
}