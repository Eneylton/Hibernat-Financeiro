package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.java.modelo.Financeiro;

public class FluxoCaixaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarTodos() {
		return em.createQuery("from Financeiro f ").getResultList();
	}

	public double totalFaturamentoDiario(Long id) {
		Query query = em
				.createNativeQuery("SELECT " 
						+ "IFNULL(sum(fin.valor), 0) AS total "
						+ "FROM "
						+ "financeiro AS fin "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = fin.empresa_id) "
						+ "WHERE "
						+ "fin.empresa_id =:paramID "
						+ "AND fin.status = '1' "
						+ "AND fin.tipo = '1' "
						+ "AND fin.destino = '0' "
						+ "AND fin.data = CURDATE() ") 
						.setParameter("paramID", id);

						return (double) query.getSingleResult();

	}
	
	
	
	
	public double totalDespesaEmpresa(Long id) {
		Query query = em
				.createNativeQuery("SELECT " 
						+ "IFNULL(sum(fin.valor), 0) AS total "
						+ "FROM "
						+ "financeiro AS fin "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = fin.empresa_id) "
						+ "WHERE "
						+ "fin.empresa_id =:paramID "
						+ "AND fin.status = '1' "
						+ "AND fin.tipo = '0' "
						+ "AND fin.destino = '0'") 
						.setParameter("paramID", id);

						return (double) query.getSingleResult();

	}
	
	
	public double totalReceber(Long id) {
		Query query = em
				.createNativeQuery("SELECT " 
						+ "IFNULL(sum(fin.valor), 0) AS total "
						+ "FROM "
						+ "financeiro AS fin "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = fin.empresa_id) "
						+ "WHERE "
						+ "fin.empresa_id =:paramID "
						+ "AND fin.status = '0' "
						+ "AND fin.tipo = '1' "
						+ "AND fin.data > CURDATE() ") 
						.setParameter("paramID", id);

						return (double) query.getSingleResult();

	}
	
	
	public double totalPagar(Long id) {
		Query query = em
				.createNativeQuery("SELECT " 
						+ "IFNULL(sum(fin.valor), 0) AS total "
						+ "FROM "
						+ "financeiro AS fin "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = fin.empresa_id) "
						+ "WHERE "
						+ "fin.empresa_id =:paramID "
						+ "AND fin.status = '0' "
						+ "AND fin.tipo = '0' "
						+ "AND fin.destino = '0' "
						+ "AND fin.data > CURDATE()") 
						.setParameter("paramID", id);

						return (double) query.getSingleResult();

	}
	
	
	public double movimentacaoBancaria(Long id) {
		Query query = em
				.createNativeQuery(" Select (r.receita) - (d.despesa) as MovimentacaoBancaria "
						+ "FROM (SELECT IFNULL(sum(rec.valor), 0) AS receita "
						+ "FROM "
						+ "financeiro AS rec "
						+ "WHERE "
						+ "rec.status = '1' "
						+ "AND rec.tipo = '1' " 
						+ "AND rec.destino = '1' ) as r, " 
				        + "(SELECT IFNULL(sum(desp.valor), 0) AS despesa "
						+ "FROM "
						+ "financeiro AS desp "
						+ "WHERE "
						+ "desp.status = '1' "
						+ "AND desp.tipo = '0' " 
						+ "AND desp.destino = '1' ) as d inner join  financeiro as fin "
						+ "inner join empresa as emp "
				        + "ON (emp.id = fin.empresa_id) where emp.id =:paramID ") 
						.setParameter("paramID", id);

		return (double) query.getSingleResult();

	}

	public double totalPendenciaBanco(Long id) {
			Query query = em
					.createNativeQuery("SELECT "
							+ "IFNULL(sum(fin.valor), 0) AS total "
							+ "FROM "
							+ "financeiro AS fin "
							+ "INNER JOIN "
							+ "empresa AS emp ON (emp.id = fin.empresa_id) "
							+ "WHERE "
							+ "empresa_id =:paramID " 
							+ "AND fin.status = '0' "
							+ "AND fin.tipo = '1' "
							+ "AND fin.destino = '1' "
							+ "AND fin.data < CURDATE()") 
							.setParameter("paramID", id);
	
							return (double) query.getSingleResult();
	
		}
	
	
	public double totalPendenciaEmpresa(Long id) {
		Query query = em
				.createNativeQuery("SELECT "
						+ "IFNULL(sum(fin.valor), 0) AS total "
						+ "FROM "
						+ "financeiro AS fin "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = fin.empresa_id) "
						+ "WHERE "
						+ "empresa_id =:paramID " 
						+ "AND fin.status = '0' "
						+ "AND fin.tipo = '1' "
						+ "AND fin.destino = '0' "
						+ "AND fin.data < CURDATE()") 
						.setParameter("paramID", id);

						return (double) query.getSingleResult();

	}



}
