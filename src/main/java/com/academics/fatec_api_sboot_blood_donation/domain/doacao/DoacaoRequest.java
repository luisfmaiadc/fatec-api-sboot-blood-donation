package com.academics.fatec_api_sboot_blood_donation.domain.doacao;

import jakarta.validation.constraints.NotNull;

public record DoacaoRequest(@NotNull Integer idDoador, @NotNull Integer idEnfermeiro) {
}
