package com.academics.fatec_api_sboot_blood_donation.domain.transfusao;

import jakarta.validation.constraints.NotNull;

public record TransfusaoRequest(@NotNull Integer idPaciente, @NotNull Integer idDoacao, @NotNull Integer idEnfermeiro) {
}
