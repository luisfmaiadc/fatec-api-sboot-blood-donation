package com.academics.fatec_api_sboot_blood_donation.repository;

import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, Integer> {
}
