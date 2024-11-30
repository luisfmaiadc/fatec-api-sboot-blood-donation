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
import java.time.Period;
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
    private Boolean ativo = Boolean.TRUE;
    private String email;
    private String telefone;

    @OneToMany(mappedBy = "doador")
    private List<Doacao> doacoes;

    public String getNomeDoadorCompleto() {
        return getNome() + " " + getSobrenome();
    }

    public Doador(DoadorRequest request) {
        this.nome = request.nome();
        this.sobrenome = request.sobrenome();
        this.genero = request.genero();
        this.dataNascimento = request.dataNascimento();
        this.tipoSanguineo = request.tipoSanguineo();
        this.email = request.email();
        this.telefone = request.telefone();
    }

    public void atualizarDoador(UpdateDoadorRequest request) {
        if (request.nome() != null && !request.nome().trim().isEmpty()) {
            this.nome = request.nome();
        }

        if (request.sobrenome() != null && !request.sobrenome().trim().isEmpty()) {
            this.sobrenome = request.sobrenome();
        }

        if (request.genero() != null && !request.genero().trim().isEmpty()) {
            this.genero = request.genero();
        }

        if (request.dataNascimento() != null && Period.between(request.dataNascimento(), LocalDate.now()).getYears() >= 16) {
            this.dataNascimento = request.dataNascimento();
        }

        if (request.tipoSanguineo() != null) {
            this.tipoSanguineo = request.tipoSanguineo();
        }

        if (request.ativo() != null) {
            this.ativo = request.ativo();
        }

        if (request.email() != null && !request.email().trim().isEmpty()) {
            this.email = request.email();
        }

        if (request.telefone() != null && !request.telefone().trim().isEmpty()) {
            this.telefone = request.telefone();
        }
    }
}
