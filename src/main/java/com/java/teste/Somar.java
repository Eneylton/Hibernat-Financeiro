package com.java.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.java.modelo.Agenda;
import com.java.util.jpa.JPAUtil;

public class Somar {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();

		List<Agenda> listaAgenda = em.createNativeQuery("select a.id,"
				                                             + "a.descricao,"
				                                             + "a.titulo,"
				                                             + "a.status,"
				                                             + "a.inicio,"
				                                             + "a.fim,"
				                                             + "a.cadastro_agenda_id, "
				+ "CASE WHEN a.status = 'false' THEN 'Receita' "
				+ "WHEN a.status = 'true' THEN 'Despesa'  END, 'a.dia' "
				+ "from agenda a", Agenda.class).getResultList();

		for(Agenda al : listaAgenda){
			System.out.println("ID: " + al.getId());
			System.out.println("DESCRIÇÃO: " + al.getDescricao());
			System.out.println("STATUS: " + al.isStatus());
			System.out.println(" ");
			System.out.println("********************");
			System.out.println(" ");
		}
		System.out.println(" ");
		System.out.println("@@@@@@@@@@----@@@@@@@@@@---@@@@@@@@22");
		System.out.println("Quantidade total: " + listaAgenda.size());
	}

}
