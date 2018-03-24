package com.java.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.java.util.jpa.JPAUtil;

public class CASE_WHEN {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		Query caseQuery = em
				.createQuery("SELECT CASE WHEN a.status = false THEN 'Realizou a Prova' "
				+ "WHEN a.status = true THEN 'Não Fez a Prova' ELSE 'Aguardando....' END FROM Agenda a");

		List<String> salaries =  caseQuery.getResultList();

		for (String salary : salaries) {
			System.out.println(salary);
		}

	}

}
