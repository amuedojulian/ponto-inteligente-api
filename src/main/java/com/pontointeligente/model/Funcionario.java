package com.pontointeligente.model;

import com.pontointeligente.enums.PerfilEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private BigDecimal valorHora;
    private Float qtdHorasTrabalhaDia;
    private Float qtdHorasAlmoco;
    private PerfilEnum perfil;
    private Date dataCriacao;
    private Date dataAtualicacao;
    private Empressa empresa;
    private List<Lancamento> lancamentos;

    public Funcionario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "valor_hora", nullable = true)
    public BigDecimal getValorHora() {
        return valorHora;
    }

    @Transient
    public Optional <BigDecimal> getValorHoraOpt() {
        return Optional.ofNullable(valorHora);
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    @Column(name = "qtd_horas_trabalho_dia", nullable = true)
    public Float getQtdHorasTrabalhaDia() {
        return qtdHorasTrabalhaDia;
    }

    @Transient
    public Optional <Float> getQtdHorasTrabalhaDiaOpt() {
        return Optional.ofNullable(qtdHorasTrabalhaDia);
    }

    public void setQtdHorasTrabalhaDia(Float qtdHorasTrabalhaDia) {
        this.qtdHorasTrabalhaDia = qtdHorasTrabalhaDia;
    }

    @Column(name = "qtd_horas_almoco", nullable = true)
    public Float getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    @Transient
    public Optional <Float> getQtdHorasAlmocoOpt() {
        return Optional.ofNullable(qtdHorasAlmoco);
    }

    public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    @Column(name = "data_criacao", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "data_atualizacao", nullable = false)
    public Date getDataAtualicacao() {
        return dataAtualicacao;
    }

    public void setDataAtualicacao(Date dataAtualicacao) {
        this.dataAtualicacao = dataAtualicacao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Empressa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empressa empresa) {
        this.empresa = empresa;
    }

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualicacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualicacao = atual;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valorHora=" + valorHora +
                ", qtdHorasTrabalhaDia=" + qtdHorasTrabalhaDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                ", perfil=" + perfil +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualicacao=" + dataAtualicacao +
                ", empresa=" + empresa +
                ", lancamentos=" + lancamentos +
                '}';
    }
}
