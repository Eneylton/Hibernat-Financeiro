package com.java.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Agenda;
import com.java.modelo.CadastroAgenda;
import com.java.modelo.Veiculo;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class CadastroAgendaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public CadastroAgenda retornarCadastroAgendaPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<CadastroAgenda> listarCadastroAgendas = (List<CadastroAgenda>) 
		em.createQuery("from CadastroAgenda c "
				     + "inner join fetch c.aluno "
				     + "inner join fetch c.instrutor "
				     + "inner join fetch c.veiculo "
		             + "where c.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarCadastroAgendas != null) {
			return listarCadastroAgendas.get(0);
		} else {
			return null;
		}

	}
	
	
	@SuppressWarnings("unchecked")
	public List<CadastroAgenda> listarTodos() {
		return em.createQuery("from CadastroAgenda ca order by ca.id desc ").getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> buscarVeiculoPorInstrutor(Long idInstrutor) {
		return em
				.createQuery(
						"select v from Veiculo v " 
				      + "inner join fetch v.instrutor " 
					  + "where instrutor_id = :paramInstrutor")
				.setParameter("paramInstrutor", idInstrutor).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<CadastroAgenda> listarAgendamentoPorEmpresa(Long id) {
						List<CadastroAgenda> listaAg = em.createNativeQuery("SELECT * "
								+ "FROM "
								+ "cadastro_agenda AS ca "
								+ "INNER JOIN "
								+ "aluno AS al ON (ca.aluno_id = al.id) "
								+ "INNER JOIN "
								+ "instrutor AS it ON (ca.instrutor_id = it.id) "
								+ "INNER JOIN "
								+ "veiculo AS vei ON (ca.veiculo_id = vei.id) "
								+ "WHERE al.id = :paramID ", CadastroAgenda.class)
				    .setParameter("paramID", id)
				    .getResultList();
		
		return listaAg;
	}
	

	@SuppressWarnings("unchecked")
	public List<Agenda> listarPrevisao(Long id) {
		List<Agenda> listaAg = em.createNativeQuery("SELECT " 
				                + "* "
								+ "FROM "
								+ "agenda ag "
								+ "INNER JOIN "
								+ "cadastro_agenda AS ca ON (ag.cadastro_agenda_id = ca.id) "
								+ "INNER JOIN "
								+ "instrutor AS it ON (it.id = ca.instrutor_id) "
								+ "INNER JOIN "
								+ "veiculo AS vei ON (vei.id = ca.veiculo_id) "
								+ "INNER JOIN "
								+ "aluno AS al ON (al.id = ca.aluno_id) "
				                + "Where al.empresa_id = :paramID "
				                + "ORDER BY current_date() desc", Agenda.class)
				                  .setParameter("paramID", id).getResultList();

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
	public void salvar(CadastroAgenda cadastroAgenda) {
		em.merge(cadastroAgenda);
	}

	@Transactional
	public void excluir(CadastroAgenda cadastroAgenda) throws NegocioException {
		CadastroAgenda excluir = em.find(CadastroAgenda.class, cadastroAgenda.getId());
		em.remove(excluir);
		em.flush();
	}

}
