package com.academics.fatec_api_sboot_blood_donation.domain.doacao;

import com.academics.fatec_api_sboot_blood_donation.domain.doador.Doador;
import com.academics.fatec_api_sboot_blood_donation.domain.enfermeiro.Enfermeiro;
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
@Table(name = "doacao")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doador")
    private Doador doador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enfermeiro")
    private Enfermeiro enfermeiro;

    private LocalDateTime dataDoacao = LocalDateTime.now();

    public Doacao(Doador doador, Enfermeiro enfermeiro) {
        this.doador = doador;
        this.enfermeiro = enfermeiro;
    }
}
