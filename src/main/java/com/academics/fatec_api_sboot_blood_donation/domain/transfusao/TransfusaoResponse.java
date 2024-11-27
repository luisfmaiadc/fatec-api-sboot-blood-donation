package com.academics.fatec_api_sboot_blood_donation.domain.transfusao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransfusaoResponse(@NotNull Integer idTransfusao, @NotBlank String nomePaciente, @NotNull Integer idDoacao,
                                 @NotBlank String nomeEnfermeiro, @NotNull LocalDateTime dataTransfusao) {

    public TransfusaoResponse(Transfusao transfusao) {
        this(transfusao.getId(), transfusao.getPaciente().getNomePacienteCompleto(), transfusao.getDoacao().getId(),
                transfusao.getEnfermeiro().getNomeEnfermeiroCompleto(), transfusao.getDataTransfusao());
    }
}
