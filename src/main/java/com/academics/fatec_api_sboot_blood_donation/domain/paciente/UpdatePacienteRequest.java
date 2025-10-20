package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePacienteRequest(@NotNull Integer idPaciente, Character genero,
                                    String email, @Size(min = 11, max = 11) String telefone) {
}