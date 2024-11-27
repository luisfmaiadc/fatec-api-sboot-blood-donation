package com.academics.fatec_api_sboot_blood_donation.domain.doador;

import com.academics.fatec_api_sboot_blood_donation.domain.doacao.Doacao;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineo;
import com.academics.fatec_api_sboot_blood_donation.domain.paciente.TipoSanguineoConverter;
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
@Table(name = "doador")
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String sobrenome;
    private String genero;
    private LocalDate dataNascimento;
    @Convert(converter = TipoSanguineoConverter.class)
    private TipoSanguineo tipoSanguineo;
    private LocalDate ultimaDoacao;
    private String email;
    private String telefone;

    @OneToMany(mappedBy = "doador")
    private List<Doacao> doacoes;

    public String getNomeDoadorCompleto() {
        return getNome() + " " + getSobrenome();
    }
}
