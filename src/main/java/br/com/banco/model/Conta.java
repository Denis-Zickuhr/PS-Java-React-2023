package br.com.banco.model;

import javax.persistence.*;

@Entity
@Table(name = "CONTA")
public class Conta {

    public Conta() {
    }

    public Conta(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA")
    private Long id;

    @Column(name = "NOME_RESPONSAVEL")
    private String nomeResponsavel;

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public Long getId() {
        return this.id;
    }
}
