package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.PacienteRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.UpdatePacienteRequest;

import java.util.List;

public interface PacienteService {

    Paciente cadastrarPaciente(PacienteRequest request);
    Paciente atualizarPaciente(UpdatePacienteRequest request);
    List<Paciente> pesquisarPacientes();
}
