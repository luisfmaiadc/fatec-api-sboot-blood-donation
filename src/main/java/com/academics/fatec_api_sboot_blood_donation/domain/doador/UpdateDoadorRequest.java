package com.academics.fatec_api_sboot_blood_donation.domain.doador;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record UpdateDoadorRequest(@NotNull Integer idDoador, Character genero, Boolean ativo,
                                  String email, @Size(min = 11, max = 11) String telefone) {
}
