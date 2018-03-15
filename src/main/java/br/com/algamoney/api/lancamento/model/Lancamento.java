package br.com.algamoney.api.lancamento.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.algamoney.api.categoria.model.Categoria;
import br.com.algamoney.api.pessoa.model.Pessoa;

@Entity
@Table(name = "lancamento")
public class Lancamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotNull
  @Size(min = 5, max = 50)
  private String descricao;

  @NotNull
  @Column(name = "data_vencimento")
  private LocalDate vencimento;

  @Column(name = "data_pagamento")
  private LocalDate pagamento;

  @NotNull
  private Double valor;

  private String observacao;

  @NotNull
  @Enumerated(EnumType.STRING)
  private EnumTipoLancamento tipo;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "codigo_categoria")
  private Categoria categoria;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "codigo_pessoa")
  private Pessoa pessoa;

  @Transient
  @JsonIgnore
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataVencimentoDe;

  @Transient
  @JsonIgnore
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataVencimentoAte;

  public Lancamento() {
    super();
  }

  public Lancamento( Long codigo, @NotNull @Size(min = 5, max = 50) String descricao, @NotNull LocalDate vencimento,
      LocalDate pagamento, Double valor, String observacao, EnumTipoLancamento tipo, Categoria categoria,
      Pessoa pessoa ) {
    super();
    this.codigo = codigo;
    this.descricao = descricao;
    this.vencimento = vencimento;
    this.pagamento = pagamento;
    this.valor = valor;
    this.observacao = observacao;
    this.tipo = tipo;
    this.categoria = categoria;
    this.pessoa = pessoa;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo( Long codigo ) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao( String descricao ) {
    this.descricao = descricao;
  }

  public LocalDate getVencimento() {
    return vencimento;
  }

  public void setVencimento( LocalDate vencimento ) {
    this.vencimento = vencimento;
  }

  public LocalDate getPagamento() {
    return pagamento;
  }

  public void setPagamento( LocalDate pagamento ) {
    this.pagamento = pagamento;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor( Double valor ) {
    this.valor = valor;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao( String observacao ) {
    this.observacao = observacao;
  }

  public EnumTipoLancamento getTipo() {
    return tipo;
  }

  public void setTipo( EnumTipoLancamento tipo ) {
    this.tipo = tipo;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria( Categoria categoria ) {
    this.categoria = categoria;
  }

  public Pessoa getPessoa() {
    return pessoa;
  }

  public void setPessoa( Pessoa pessoa ) {
    this.pessoa = pessoa;
  }

  public LocalDate getDataVencimentoDe() {
    return dataVencimentoDe;
  }

  public void setDataVencimentoDe( LocalDate dataVencimentoDe ) {
    this.dataVencimentoDe = dataVencimentoDe;
  }

  public LocalDate getDataVencimentoAte() {
    return dataVencimentoAte;
  }

  public void setDataVencimentoAte( LocalDate dataVencimentoAte ) {
    this.dataVencimentoAte = dataVencimentoAte;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
    return result;
  }

  @Override
  public boolean equals( Object obj ) {
    if ( this == obj )
      return true;
    if ( obj == null )
      return false;
    if ( getClass() != obj.getClass() )
      return false;
    Lancamento other = (Lancamento) obj;
    if ( codigo == null ) {
      if ( other.codigo != null )
        return false;
    } else if ( !codigo.equals( other.codigo ) )
      return false;
    return true;
  }

}
