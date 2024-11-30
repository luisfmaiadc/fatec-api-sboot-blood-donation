package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdatePacienteRequest(@NotNull Integer idPaciente, String nome, String sobrenome,
                                    @Size(min = 1, max = 1) String genero,
                                    LocalDate dataNascimento, TipoSanguineo tipoSanguineo,
                                    String email, @Size(min = 11, max = 11) String telefone) {
}
