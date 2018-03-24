package com.java.teste;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.java.modelo.Agenda;
import com.java.util.jpa.JPAUtil;

public class ConsultaoroData {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();

		Calendar cal = Calendar.getInstance();
	

		List<Agenda> listaAgenda = em
				.createNativeQuery("select * from agenda ", Agenda.class)
				.getResultList();

		for (Agenda al : listaAgenda) {
			System.out.println("ID: " + al.getId());
			System.out.println("DATA: " + al.getFim());
			cal.getTimeInMillis();
			cal.setTime(al.getFim());
			Integer dia = cal.get(Calendar.DAY_OF_MONTH);

			switch (dia) {
			case 16:
				System.out.println("Dia do evendo");
				break;

			case 17:
				System.out.println("Um Dia do evendo");
				break;

			case 18:
				System.out.println("Dois Dia do evendo");
				break;

			case 19:
				System.out.println("Três Dia do evendo");
				break;

			case 20:
				System.out.println("Quatro Dia do evendo");
				break;

			case 5:
				System.out.println("Cinco Dia do evendo");
				break;

			}

		}

	}

}
