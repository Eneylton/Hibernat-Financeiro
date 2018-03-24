package com.java.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the cadastro_agenda database table.
 * 
 */
@Entity
@Table(name = "cadastro_agenda")
@NamedQuery(name = "CadastroAgenda.findAll", query = "SELECT c FROM CadastroAgenda c")
public class CadastroAgenda implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private List<Agenda> agendas;
	private Aluno aluno;
	private Instrutor instrutor;
	private Veiculo veiculo;

	public CadastroAgenda() {
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

	// bi-directional many-to-one association to Agenda
	@OneToMany(mappedBy = "cadastroAgenda")
	public List<Agenda> getAgendas() {
		return this.agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public Agenda addAgenda(Agenda agenda) {
		getAgendas().add(agenda);
		agenda.setCadastroAgenda(this);

		return agenda;
	}

	public Agenda removeAgenda(Agenda agenda) {
		getAgendas().remove(agenda);
		agenda.setCadastroAgenda(null);

		return agenda;
	}

	// bi-directional many-to-one association to Aluno
	@ManyToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
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

	// bi-directional many-to-one association to Veiculo
	@ManyToOne
	@JoinColumn(name = "veiculo_id", nullable = false)
	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
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
		CadastroAgenda other = (CadastroAgenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}