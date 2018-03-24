package com.java.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the aluno database table.
 * 
 */
@Entity
@Table(name = "aluno")
@NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String sobreNome;
	private List<CadastroAgenda2> cadastroAgenda2s;
	private EstadoCivil estadoCivil;
	private Sexo sexo;
	private Usuario usuario;
	private Empresa empresa;
	private Servico servico;
	private String cep;
	private String endereco;
	private String bairro;
	private String numero;
	private String cidade;
	private String estado;
	private Date dataNascimento;
	private String foto;
	private String cpf;
	private String rg;
	private String email;
	private String fixo;
	private String wathsapp;
	private String enderecoCompleto;
	private float longitude;
	private float latitude;
	private String previsao;

	private List<CadastroAgenda> cadastroAgendas;

	public Aluno() {
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

	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFixo() {
		return fixo;
	}

	public void setFixo(String fixo) {
		this.fixo = fixo;
	}

	public String getWathsapp() {
		return wathsapp;
	}

	public void setWathsapp(String wathsapp) {
		this.wathsapp = wathsapp;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// bi-directional many-to-one association to CadastroAgenda2
	@OneToMany(mappedBy = "aluno")
	public List<CadastroAgenda2> getCadastroAgenda2s() {
		return this.cadastroAgenda2s;
	}

	public void setCadastroAgenda2s(List<CadastroAgenda2> cadastroAgenda2s) {
		this.cadastroAgenda2s = cadastroAgenda2s;
	}

	public CadastroAgenda2 addCadastroAgenda2(CadastroAgenda2 cadastroAgenda2) {
		getCadastroAgenda2s().add(cadastroAgenda2);
		cadastroAgenda2.setAluno(this);

		return cadastroAgenda2;
	}

	public CadastroAgenda2 removeCadastroAgenda2(CadastroAgenda2 cadastroAgenda2) {
		getCadastroAgenda2s().remove(cadastroAgenda2);
		cadastroAgenda2.setAluno(null);

		return cadastroAgenda2;
	}

	// bi-directional many-to-one association to EstadoCivil
	@ManyToOne
	@JoinColumn(name = "estado_civil_id", nullable = false)
	public EstadoCivil getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	// bi-directional many-to-one association to Sexo
	@ManyToOne
	@JoinColumn(name = "sexo_id", nullable = false)
	public Sexo getSexo() {
		return this.sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
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
	@JoinColumn(name = "servico_id", nullable = false)
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	// bi-directional many-to-one association to CadastroAgenda
	@OneToMany(mappedBy = "aluno")
	public List<CadastroAgenda> getCadastroAgendas() {
		return this.cadastroAgendas;
	}

	public void setCadastroAgendas(List<CadastroAgenda> cadastroAgendas) {
		this.cadastroAgendas = cadastroAgendas;
	}

	public CadastroAgenda addCadastroAgenda(CadastroAgenda cadastroAgenda) {
		getCadastroAgendas().add(cadastroAgenda);
		cadastroAgenda.setAluno(this);

		return cadastroAgenda;
	}

	public CadastroAgenda removeCadastroAgenda(CadastroAgenda cadastroAgenda) {
		getCadastroAgendas().remove(cadastroAgenda);
		cadastroAgenda.setAluno(null);

		return cadastroAgenda;
	}

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	
	@Transient
	public String getPrevisao() {
		return previsao;
	}

	public void setPrevisao(String previsao) {
		this.previsao = previsao;
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
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}