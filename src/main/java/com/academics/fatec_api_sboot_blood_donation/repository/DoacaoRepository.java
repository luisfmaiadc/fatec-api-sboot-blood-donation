package com.academics.fatec_api_sboot_blood_donation.repository;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Integer> {
}
