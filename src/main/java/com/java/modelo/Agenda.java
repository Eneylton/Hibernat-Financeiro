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
import javax.persistence.Transient;

/**
 * The persistent class for the agenda database table.
 * 
 */
@Entity
@Table(name = "agenda")
@NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a")
public class Agenda implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String descricao;
	private Date fim;
	private Date inicio;
	private boolean status;
	private String dia;
	private String previsao;
	private String titulo;
	private CadastroAgenda cadastroAgenda;
	private Empresa empresa;

	public Agenda() {
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

	@Column(length = 255)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Transient
	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	@Column(length = 225)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	// bi-directional many-to-one association to CadastroAgenda
	@ManyToOne
	@JoinColumn(name = "cadastro_agenda_id", nullable = false)
	public CadastroAgenda getCadastroAgenda() {
		return this.cadastroAgenda;
	}

	public void setCadastroAgenda(CadastroAgenda cadastroAgenda) {
		this.cadastroAgenda = cadastroAgenda;
	}

	@Transient
	public String getPrevisao() {
		return previsao;
	}

	public void setPrevisao(String previsao) {
		this.previsao = previsao;
	}

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}