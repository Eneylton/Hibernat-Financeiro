package com.java.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Agenda;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class AgendaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Agenda retornarAgendaPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Agenda> listarAgendas = (List<Agenda>) em
				.createQuery("from Agenda a " + "inner join fetch a.cadastroAgenda " + "where a.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarAgendas != null) {
			return listarAgendas.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Agenda> listarTodos() {
		return em.createQuery("from Agenda a ").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Agenda> listarTodosIDagenda(Long id) {
		return em.createNativeQuery("SELECT * FROM agenda as a Where a.cadastro_agenda_id = :paramID")
				.setParameter("paramID", id)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Agenda> listarTodosPorEmpresa(Long id) {
		return em.createQuery("select a from Agenda a " + "inner join fetch a.empresa "
						+ "where empresa_id = :paramID ORDER BY a.id desc")
				.setParameter("paramID", id).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Agenda> listarPrevisao() {
		List<Agenda> listaAg = em.createNativeQuery("select * from agenda a ", Agenda.class).getResultList();

		Calendar cal = new GregorianCalendar();

		Calendar cal2 = new GregorianCalendar();

		for (Agenda ag : listaAg) {

			ag.getId();

			if (ag.isStatus() == true) {

				ag.setDia("Aula Realizada !!!");

			} else {

				ag.setDia("Aguardando.....");
			}

			Date hoje = new Date();
			cal2.setTime(hoje);
			cal.setTime(ag.getFim());
			Integer dia = cal.get(Calendar.DAY_OF_YEAR);
			Integer diaAtual = cal2.get(Calendar.DAY_OF_YEAR);
			Integer previsao = dia - diaAtual;
			
			if (previsao < 0) {

				ag.setPrevisao("7");
				
			} else {
			
			switch (previsao) {
			case 0:
				ag.setPrevisao("0");
				break;

			case 1:
				ag.setPrevisao("1");
				break;

			case 2:
				ag.setPrevisao("2");
				break;

			case 3:
				ag.setPrevisao("3");
				break;
				
			case 4:
				ag.setPrevisao("4");
				break;
				
			case 5:
				ag.setPrevisao("5");
				break;

			default:
				ag.setPrevisao("6");
				break;
			}
			}
		}
		return listaAg;
	}

	@Transactional
	public void salvar(Agenda agenda) {
		em.merge(agenda);
	}

	@Transactional
	public void excluir(Agenda agenda) throws NegocioException {
		Agenda excluir = em.find(Agenda.class, agenda.getId());
		em.remove(excluir);
		em.flush();
	}

}
