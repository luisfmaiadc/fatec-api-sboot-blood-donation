package com.academics.fatec_api_sboot_blood_donation.domain.paciente;

import com.academics.fatec_api_sboot_blood_donation.domain.transfusao.Transfusao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String sobrenome;
    private String genero;
    private LocalDate dataNascimento;
    @Convert(converter = TipoSanguineoConverter.class)
    private TipoSanguineo tipoSanguineo;
    private String email;
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    private List<Transfusao> transfusoes;
}
