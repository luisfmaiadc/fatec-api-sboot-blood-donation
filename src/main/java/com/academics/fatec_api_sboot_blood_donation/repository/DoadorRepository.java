package com.academics.fatec_api_sboot_blood_donation.repository;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoadorRepository extends JpaRepository<Doador, Integer> {
    List<Doador> findByTipoSanguineo(TipoSanguineo tipoSanguineo);
}