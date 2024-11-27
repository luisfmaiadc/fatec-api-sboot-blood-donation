package com.academics.fatec_api_sboot_blood_donation.repository;

import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.Transfusao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfusaoRepository extends JpaRepository<Transfusao, Integer> {
}
