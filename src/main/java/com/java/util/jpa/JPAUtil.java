package com.java.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernatPerciste");
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
}
