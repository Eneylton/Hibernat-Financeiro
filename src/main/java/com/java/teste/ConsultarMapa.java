package com.java.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.java.modelo.Agenda;
import com.java.util.jpa.JPAUtil;

public class ConsultarMapa {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		Long idInstrutor = 4L;
		List<Agenda> listaAgenda = em.createNativeQuery("SELECT * FROM agenda ag "
				+ "INNER JOIN "
				+ "cadastro_agenda ca ON (ca.id = ag.cadastro_agenda_id) "
				+ "INNER JOIN "
				+ "instrutor it ON (ca.instrutor_id = it.id) "
				+ "INNER JOIN "
				+ "aluno al ON (ca.aluno_id = al.id) "
				+ "INNER JOIN "
				+ "veiculo vei ON (vei.instrutor_id = it.id) Where it.id = :paramId", Agenda.class)
				.setParameter("paramId", idInstrutor).getResultList();

				for(Agenda al : listaAgenda){
					System.out.println("ID: " + al.getId());
					
					System.out.println("STATUS: " + al.isStatus());
					System.out.println("INICIO: " + al.getInicio());
					System.out.println("FIM: " + al.getInicio());
					System.out.println("INSTRUTOR: " + al.getCadastroAgenda().getInstrutor().getNome());
					System.out.println("ALUNO: " + al.getCadastroAgenda().getAluno().getNome());
					
					System.out.println(" ");
					System.out.println("********************");
					System.out.println(" ");
				}

	}

}
