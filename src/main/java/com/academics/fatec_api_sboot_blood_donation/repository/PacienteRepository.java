package com.academics.fatec_api_sboot_blood_donation.repository;

import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
