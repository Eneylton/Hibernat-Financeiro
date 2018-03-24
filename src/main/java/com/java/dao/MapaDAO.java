package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Agenda;

public class MapaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Agenda> listarTodos() {
		return em.createNativeQuery("SELECT * FROM agenda ag "
				+ "INNER JOIN "
				+ "cadastro_agenda ca ON (ca.id = ag.cadastro_agenda_id) "
				+ "INNER JOIN "
				+ "instrutor it ON (ca.instrutor_id = it.id) "
				+ "INNER JOIN "
				+ "aluno al ON (ca.aluno_id = al.id) "
				+ "INNER JOIN "
				+ "veiculo vei ON (ca.veiculo_id = vei.id) ", Agenda.class)
				.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Agenda> listarTodosPorInstrutor(Long idInstrutor ) {
		return em.createNativeQuery("SELECT * FROM agenda ag "
				+ "INNER JOIN "
				+ "cadastro_agenda ca ON (ca.id = ag.cadastro_agenda_id) "
				+ "INNER JOIN "
				+ "instrutor it ON (ca.instrutor_id = it.id) "
				+ "INNER JOIN "
				+ "aluno al ON (ca.aluno_id = al.id) "
				+ "INNER JOIN "
				+ "veiculo vei ON (ca.veiculo_id = vei.id) Where it.id = :paramId", Agenda.class)
				.setParameter("paramId", idInstrutor).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Agenda> listarTodosPorAluno(Long idALuno) {
		return em.createNativeQuery("SELECT * FROM agenda ag "
				+ "INNER JOIN "
				+ "cadastro_agenda ca ON (ca.id = ag.cadastro_agenda_id) "
				+ "INNER JOIN "
				+ "instrutor it ON (ca.instrutor_id = it.id) "
				+ "INNER JOIN "
				+ "aluno al ON (ca.aluno_id = al.id) "
				+ "INNER JOIN "
				+ "veiculo vei ON (ca.veiculo_id = vei.id) Where al.id = :paramId", Agenda.class)
				.setParameter("paramId", idALuno).getResultList();
	}

	
	
	

}
