package com.academics.fatec_api_sboot_blood_donation.domain.doador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DoadorResponse(@NotBlank String nomeCompleto, @NotBlank String genero,
                             @NotNull LocalDate dataNascimento, @NotBlank String tipoSanguineo,
                             @NotBlank String email, @NotBlank String telefone, LocalDate ultimaDoacao) {

    public DoadorResponse(Doador doador) {
        this(doador.getNomeDoadorCompleto(), doador.getGenero(), doador.getDataNascimento(),
                doador.getTipoSanguineo().getDescricao(), doador.getEmail(), doador.getTelefone(),
                doador.getUltimaDoacao());
    }
}
