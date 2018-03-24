package com.java.teste;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.java.modelo.Agenda;
import com.java.util.jpa.JPAUtil;

public class ManipuladoDataPrevisao {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();

		List<Agenda> listaAgenda = em.createNativeQuery("select * from agenda a", Agenda.class).getResultList();

		Calendar cal = new GregorianCalendar();

		Calendar cal2 = new GregorianCalendar();

		for (Agenda al : listaAgenda) {
			Date hoje = new Date();
			cal2.setTime(hoje);
			cal.setTime(al.getFim());
			Integer dia = cal.get(Calendar.DAY_OF_MONTH);
			Integer diaAtual = cal2.get(Calendar.DAY_OF_MONTH);

			Integer previsao = dia - diaAtual;

			switch (previsao) {
			case 0:
				System.out.println("DIA DA AVALIAÇÃO");
				break;

			case 1:
				System.out.println("UMA DIA PARA AVALIAÇÃO");
				break;

			case 2:
				System.out.println("DOIS DIAS DA AVALIAÇÃO");
				break;

			case 3:
				System.out.println("TRÊS DIAS DA AVALIAÇÃO");
				break;

			default:
				System.out.println("PRAZO INSPIRADO !!!!!!!!");
				break;
			}

		}

	}

}
