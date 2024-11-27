package com.academics.fatec_api_sboot_blood_donation.repository;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Integer> {
}
