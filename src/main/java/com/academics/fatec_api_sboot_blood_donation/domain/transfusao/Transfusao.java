package com.academics.fatec_api_sboot_blood_donation.domain.transfusao;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "transfusao")
public class Transfusao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doacao")
    private Doacao doacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enfermeiro")
    private Enfermeiro enfermeiro;

    private LocalDateTime dataTransfusao = LocalDateTime.now();

    public Transfusao(Paciente paciente, Doacao doacao, Enfermeiro enfermeiro) {
        this.paciente = paciente;
        this.doacao = doacao;
        this.enfermeiro = enfermeiro;
    }
}
