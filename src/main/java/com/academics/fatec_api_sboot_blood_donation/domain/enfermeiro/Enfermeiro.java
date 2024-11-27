package com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
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
@Table(name = "enfermeiro")
public class Enfermeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String sobrenome;
    private String genero;
    private String email;
    private String telefone;

    @OneToMany(mappedBy = "enfermeiro")
    private List<Doacao> doacoes;

    @OneToMany(mappedBy = "enfermeiro")
    private List<Transfusao> transfusoes;

    public String getNomeEnfermeiroCompleto() {
        return getNome() + " " + getSobrenome();
    }
}
