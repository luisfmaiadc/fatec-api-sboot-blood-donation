package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PacienteResponse(@NotBlank String nomeCompleto, @NotBlank String genero,
                               @NotNull LocalDate dataNascimento, @NotBlank String tipoSanguineo,
                               @NotBlank String email, @NotBlank String telefone) {

    public PacienteResponse(Paciente paciente) {
        this(paciente.getNomePacienteCompleto(), paciente.getGenero(), paciente.getDataNascimento(),
                paciente.getTipoSanguineo().getDescricao(), paciente.getEmail(), paciente.getTelefone());
    }
}
