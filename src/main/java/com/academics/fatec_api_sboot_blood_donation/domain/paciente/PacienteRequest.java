package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteRequest(@NotBlank String nome, @NotBlank String sobrenome,
                              @NotBlank @Size(min = 1, max = 1) String genero,
                              @NotNull LocalDate dataNascimento, @NotNull TipoSanguineo tipoSanguineo,
                              @NotBlank String email, @NotBlank @Size(min = 11, max = 11) String telefone) {
}
