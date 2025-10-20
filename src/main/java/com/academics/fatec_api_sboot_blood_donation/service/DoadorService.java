package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.DoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.doador.UpdateDoadorRequest;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.AgeException;
import com.academics.fatec_api_sboot_blood_donation.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    public Doador cadastrarDoador(DoadorRequest request) {
        verificarIdadeMinima(request.dataNascimento());
        Doador doador = new Doador(request);
        doadorRepository.save(doador);
        return doador;
    }

    private void verificarIdadeMinima(LocalDate dataNascimento) {
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 16) {
            throw new AgeException("Idade insuficiente para se tornar doador.");
        }
    }

    public List<Doador> pesquisarPorTipoSanguineo(TipoSanguineo tipoSanguineo) {
        return doadorRepository.findByTipoSanguineo(tipoSanguineo);
    }

    public void desativarDoador(Integer id) {
        Doador doador = doadorRepository.getReferenceById(id);
        doador.setAtivo(Boolean.FALSE);
    }

    public Doador atualizarDoador(UpdateDoadorRequest request) {
        Doador doador = doadorRepository.getReferenceById(request.idDoador());
        atualizarDoador(request, doador);
        doadorRepository.save(doador);
        return doador;
    }

    private void atualizarDoador(UpdateDoadorRequest request, Doador doador) {
        if (request.genero() != null) {
            doador.setGenero(request.genero());
        }

        if (request.ativo() != null) {
            doador.setAtivo(request.ativo());
        }

        if (request.email() != null) {
            doador.setEmail(request.email());
        }

        if (request.telefone() != null) {
            doador.setTelefone(request.telefone());
        }
    }
}