package com.java.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the instrutor database table.
 * 
 */
@Entity
@Table(name = "instrutor")
@NamedQuery(name = "Instrutor.findAll", query = "SELECT i FROM Instrutor i")
public class Instrutor implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Empresa empresa;
	private List<CadastroAgenda> cadastroAgendas;
	private List<Veiculo> veiculos;

	public Instrutor() {
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

	@Column(length = 225)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// bi-directional many-to-one association to CadastroAgenda
	@OneToMany(mappedBy = "instrutor")
	public List<CadastroAgenda> getCadastroAgendas() {
		return this.cadastroAgendas;
	}

	public void setCadastroAgendas(List<CadastroAgenda> cadastroAgendas) {
		this.cadastroAgendas = cadastroAgendas;
	}

	public CadastroAgenda addCadastroAgenda(CadastroAgenda cadastroAgenda) {
		getCadastroAgendas().add(cadastroAgenda);
		cadastroAgenda.setInstrutor(this);

		return cadastroAgenda;
	}

	public CadastroAgenda removeCadastroAgenda(CadastroAgenda cadastroAgenda) {
		getCadastroAgendas().remove(cadastroAgenda);
		cadastroAgenda.setInstrutor(null);

		return cadastroAgenda;
	}

	// bi-directional many-to-one association to Veiculo
	@OneToMany(mappedBy = "instrutor")
	public List<Veiculo> getVeiculos() {
		return this.veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Veiculo addVeiculo(Veiculo veiculo) {
		getVeiculos().add(veiculo);
		veiculo.setInstrutor(this);

		return veiculo;
	}

	public Veiculo removeVeiculo(Veiculo veiculo) {
		getVeiculos().remove(veiculo);
		veiculo.setInstrutor(null);

		return veiculo;
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
		Instrutor other = (Instrutor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}