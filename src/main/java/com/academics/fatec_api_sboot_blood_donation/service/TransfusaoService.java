package com.academics.fatec_api_sboot_blood_donation.service;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.Transfusao;
import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.TransfusaoRequest;
import com.academics.fatec_api_sboot_blood_donation.infra.exception.IncompatibleBloodTypeException;
import com.academics.fatec_api_sboot_blood_donation.repository.DoacaoRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.EnfermeiroRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.PacienteRepository;
import com.academics.fatec_api_sboot_blood_donation.repository.TransfusaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class TransfusaoService {

    @Autowired
    private TransfusaoRepository transfusaoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private EnfermeiroRepository enfermeiroRepository;


    public Transfusao cadastrarTranfusao(TransfusaoRequest request) {
        Paciente paciente = pacienteRepository.getReferenceById(request.idPaciente());
        Doacao doacao = doacaoRepository.getReferenceById(request.idDoacao());
        Enfermeiro enfermeiro = enfermeiroRepository.getReferenceById(request.idEnfermeiro());
        verificarTipoSanguineo(doacao.getDoador().getTipoSanguineo().getDescricao(), paciente.getTipoSanguineo().getDescricao());
        Transfusao transfusao = new Transfusao(paciente, doacao, enfermeiro);
        transfusaoRepository.save(transfusao);
        return transfusao;
    }

    private void verificarTipoSanguineo(String tipoSanguineoDoador, String tipoSanguineoPaciente) {
        Map<String, Set<String>> compatibilidadeSanguinea = Map.of(
                "A+", Set.of("A+", "A-", "O+", "O-"),
                "A-", Set.of("A-", "O-"),
                "B+", Set.of("B+", "B-", "O+", "O-"),
                "B-", Set.of("B-", "O-"),
                "AB+", Set.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"),
                "AB-", Set.of("A-", "B-", "AB-", "O-"),
                "O+", Set.of("O+", "O-"),
                "O-", Set.of("O-")
        );

        Set<String> tiposCompativeis = compatibilidadeSanguinea.get(tipoSanguineoPaciente);
        if (tiposCompativeis == null || !tiposCompativeis.contains(tipoSanguineoDoador)) {
            throw new IncompatibleBloodTypeException("Tipo sanguíneo incompatível!");
        }
    }
}