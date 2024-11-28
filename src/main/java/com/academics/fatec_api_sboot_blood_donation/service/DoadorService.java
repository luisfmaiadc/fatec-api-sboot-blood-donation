package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorResponse;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.AgeException;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    public ResponseEntity cadastrarDoador(DoadorRequest request, UriComponentsBuilder uriComponentsBuilder) {
        verificarIdadeMinima(request.dataNascimento());
        Doador doador = new Doador(request);
        doadorRepository.save(doador);
        URI uri = uriComponentsBuilder.path("/doador/{id}").buildAndExpand(doador.getId()).toUri();
        return ResponseEntity.created(uri).body(new DoadorResponse(doador));
    }

    private void verificarIdadeMinima(LocalDate dataNascimento) {
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 16) {
            throw new AgeException("Idade insuficiente para se tornar doador.");
        }
    }
}
