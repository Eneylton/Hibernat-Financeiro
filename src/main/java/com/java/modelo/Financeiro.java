package com.java.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the financeiro database table.
 * 
 */
@Entity
@Table(name = "financeiro")
@NamedQuery(name = "Financeiro.findAll", query = "SELECT f FROM Financeiro f")
public class Financeiro implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date data;
	private Date dataPagamento;
	private int quantidade;
	private String parcelaExtenso;
	private double valor;
	private Aluno aluno;
	private Servico servico;
	private CategoriaFinanceiro categoriaFinanceiro;
	private boolean status;
	private double total;
	private String msn;
	private boolean tipo;
	private String descricao;
	private boolean destino;
	private String valorExtenso;
	private Usuario usuario;
	private Empresa empresa;
	private FormaPagamento formaPagamento;
	private String previsao;
	private Date dataInicio;
	private Date dataFim;

	public Financeiro() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(precision = 10, scale = 2)
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getParcelaExtenso() {
		return parcelaExtenso;
	}

	public void setParcelaExtenso(String parcelaExtenso) {
		this.parcelaExtenso = parcelaExtenso;
	}

	@Transient
	public String getPrevisao() {
		return previsao;
	}

	public void setPrevisao(String previsao) {
		this.previsao = previsao;
	}

	@ManyToOne
	@JoinColumn(name = "servico_id", nullable = false)
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	@ManyToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@Transient
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Transient
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isDestino() {
		return destino;
	}

	public void setDestino(boolean destino) {
		this.destino = destino;
	}

	public String getValorExtenso() {
		return valorExtenso;
	}

	public void setValorExtenso(String valorExtenso) {
		this.valorExtenso = valorExtenso;
	}

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	public Empresa getEmpresa() {
		return empresa;
	}

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "categoria_financeiro_id", nullable = false)
	public CategoriaFinanceiro getCategoriaFinanceiro() {
		return categoriaFinanceiro;
	}

	public void setCategoriaFinanceiro(CategoriaFinanceiro categoriaFinanceiro) {
		this.categoriaFinanceiro = categoriaFinanceiro;
	}
	

	@Transient
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Transient
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Financeiro other = (Financeiro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}