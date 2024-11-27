package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import jakarta.persistence.AttributeConverter;

public class TipoSanguineoConverter implements AttributeConverter<TipoSanguineo, String> {

    @Override
    public String convertToDatabaseColumn(TipoSanguineo tipoSanguineo) {
        return tipoSanguineo != null ? tipoSanguineo.getDescricao() : null;
    }

    @Override
    public TipoSanguineo convertToEntityAttribute(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            return null;
        }
        for (TipoSanguineo tipo : TipoSanguineo.values()) {
            if (tipo.getDescricao().equals(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo sanguíneo inválido: " + descricao);
    }
}
