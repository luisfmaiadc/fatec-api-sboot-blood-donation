package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import lombok.Getter;

@Getter
public enum TipoSanguineo {

    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    private final String descricao;

    TipoSanguineo(String descricao) {
        this.descricao = descricao;
    }
}
