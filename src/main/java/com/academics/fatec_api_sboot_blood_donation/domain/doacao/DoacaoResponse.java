package com.academics.fatec_api_sboot_blood_donation.domain.doacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DoacaoResponse(@NotNull Integer idDoacao, @NotBlank String nomeDoador, @NotBlank String nomeEnfermeiro, @NotNull
LocalDateTime dataDoacao) {

    public DoacaoResponse(Doacao doacao) {
        this(doacao.getId(), doacao.getDoador().getNomeDoadorCompleto(), doacao.getEnfermeiro().getNomeEnfermeiroCompleto(), doacao.getDataDoacao());
    }
}
