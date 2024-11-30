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

    public String getNomePacienteCompleto() {
        return getNome() + " " + getSobrenome();
    }

    public Paciente(PacienteRequest request) {
        this.nome = request.nome();
        this.sobrenome = request.sobrenome();
        this.genero = request.genero();
        this.dataNascimento = request.dataNascimento();
        this.tipoSanguineo = request.tipoSanguineo();
        this.email = request.email();
        this.telefone = request.telefone();
    }

    public void atualizarPaciente(UpdatePacienteRequest request) {
        if (request.nome() != null && !request.nome().trim().isEmpty()) {
            this.nome = request.nome();
        }

        if (request.sobrenome() != null && !request.sobrenome().trim().isEmpty()) {
            this.sobrenome = request.sobrenome();
        }

        if (request.genero() != null && !request.genero().trim().isEmpty()) {
            this.genero = request.genero();
        }

        if (request.dataNascimento() != null) {
            this.dataNascimento = request.dataNascimento();
        }

        if (request.tipoSanguineo() != null) {
            this.tipoSanguineo = request.tipoSanguineo();
        }

        if (request.email() != null && !request.email().trim().isEmpty()) {
            this.email = request.email();
        }

        if (request.telefone() != null && !request.telefone().trim().isEmpty()) {
            this.telefone = request.telefone();
        }
    }
}
