package com.java.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the veiculo database table.
 * 
 */
@Entity
@Table(name = "veiculo")
@NamedQuery(name = "Veiculo.findAll", query = "SELECT v FROM Veiculo v")
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String especie;
	private String foto;
	private String marca;
	private List<CadastroAgenda> cadastroAgendas;
	private Instrutor instrutor;

	private Empresa empresa;

	public Veiculo() {
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
	public String getEspecie() {
		return this.especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	@Column(length = 225)
	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Column(length = 225)
	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	// bi-directional many-to-one association to CadastroAgenda
	@OneToMany(mappedBy = "veiculo")
	public List<CadastroAgenda> getCadastroAgendas() {
		return this.cadastroAgendas;
	}

	public void setCadastroAgendas(List<CadastroAgenda> cadastroAgendas) {
		this.cadastroAgendas = cadastroAgendas;
	}

	public CadastroAgenda addCadastroAgenda(CadastroAgenda cadastroAgenda) {
		getCadastroAgendas().add(cadastroAgenda);
		cadastroAgenda.setVeiculo(this);

		return cadastroAgenda;
	}

	public CadastroAgenda removeCadastroAgenda(CadastroAgenda cadastroAgenda) {
		getCadastroAgendas().remove(cadastroAgenda);
		cadastroAgenda.setVeiculo(null);

		return cadastroAgenda;
	}

	// bi-directional many-to-one association to Instrutor
	@ManyToOne
	@JoinColumn(name = "instrutor_id", nullable = false)
	public Instrutor getInstrutor() {
		return this.instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
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
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}