package br.com.banco.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSFERENCIA")
public class Transferencia {

    public Transferencia() {
    }

    public Transferencia(LocalDateTime dataTransferencia, BigDecimal valor, String tipo, String nomeOperadorTransacao, Conta conta) {
        this.dataTransferencia = dataTransferencia;
        this.valor = valor;
        this.tipo = tipo;
        this.nomeOperadorTransacao = nomeOperadorTransacao;
        this.conta = conta;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA_TRANSFERENCIA")
    private LocalDateTime dataTransferencia;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "NOME_OPERADOR_TRANSACAO")
    private String nomeOperadorTransacao;

    @ManyToOne
    @JoinColumn(name = "CONTA_ID")
    private Conta conta;

    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeOperadorTransacao() {
        return nomeOperadorTransacao;
    }

    public void setNomeOperadorTransacao(String nomeOperadorTransacao) {
        this.nomeOperadorTransacao = nomeOperadorTransacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }
}

