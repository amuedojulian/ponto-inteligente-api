package com.pontointeligente.model;

import com.pontointeligente.enums.TipoEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lancamento")
public class Lancamento {

    private Long id;
    private Date date;
    private String descricao;
    private String localicacao;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private TipoEnum tipo;
    private Funcionario funcionario;

    public Lancamento() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "descricao", nullable = true)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "localicacao", nullable = true)
    public String getLocalicacao() {
        return localicacao;
    }

    public void setLocalicacao(String localicacao) {
        this.localicacao = localicacao;
    }

    @Column(name = "data_criacao", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "data_atualizacao", nullable = false)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    public TipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnum tipo) {
        this.tipo = tipo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

    @Override
    public String toString() {
        return "Lancamento{" +
                "id=" + id +
                ", date=" + date +
                ", descricao='" + descricao + '\'' +
                ", localicacao='" + localicacao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", tipo=" + tipo +
                ", funcionario=" + funcionario +
                '}';
    }
}
