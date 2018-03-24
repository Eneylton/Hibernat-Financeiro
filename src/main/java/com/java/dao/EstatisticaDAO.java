package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Financeiro;

public class EstatisticaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarTodos() {
		return em.createQuery("from Financeiro f ").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoTipo(Long id) {
		List<Object[]> listaGraficoTipo = em
				.createNativeQuery("SELECT " + "CASE fin.tipo " + "WHEN 0 THEN 'Despesa' " + "WHEN 1 THEN 'Receita' "
						+ "ELSE 'Nenhuma' " + "END as tipo, COUNT(fin.tipo) AS total " + "FROM financeiro AS fin "
						+ "WHERE fin.empresa_id = :paramID AND fin.status='1' GROUP BY tipo")
				.setParameter("paramID", id).getResultList();

		return listaGraficoTipo;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoTipodiaReceita(Long id) {
		List<Object[]> listaGraficoTipo = em
				.createNativeQuery("SELECT dayofmonth(fin.data) as semana, " + "COUNT(fin.tipo) AS total "
						+ "FROM financeiro AS fin "
						+ "WHERE fin.empresa_id = :paramID AND fin.tipo='1' AND fin.status ='1' "
						+ "AND MONTH(fin.data) = MONTH(NOW()) GROUP BY semana")
				.setParameter("paramID", id).getResultList();

		return listaGraficoTipo;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoTipodiaDespesa(Long id) {
		List<Object[]> listaGraficoTipo = em
				.createNativeQuery("SELECT dayofmonth(fin.data) as semana, " + "COUNT(fin.tipo) AS total "
						+ "FROM financeiro AS fin " + "WHERE fin.empresa_id = :paramID AND fin.tipo='0' "
						+ "AND fin.status ='1' AND MONTH(fin.data) = MONTH(NOW()) GROUP BY semana")
				.setParameter("paramID", id).getResultList();

		return listaGraficoTipo;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoDia(Long id) {
		List<Object[]> listaGraficoTipo = em
				.createNativeQuery("SELECT day(fin.data) as dia, " + "COUNT(fin.tipo) AS total "
						+ "FROM financeiro AS fin " + "WHERE fin.empresa_id = :paramID "
						+ "AND status = '1' AND MONTH(fin.data) = MONTH(NOW()) GROUP BY dia")
				.setParameter("paramID", id).getResultList();

		return listaGraficoTipo;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoLancamentoMensalReceita(Long id) {
		List<Object[]> lista = em.createNativeQuery("SELECT (CASE MONTH(fin.data)  WHEN 1 THEN 'Janeiro' "
				+ "WHEN 2 THEN 'Fevereiro' WHEN 3 THEN 'Março' WHEN 4 THEN 'Abril' "
				+ "WHEN 5 THEN 'Maio' WHEN 6 THEN 'Junho' WHEN 7 THEN 'Julho' WHEN 8 THEN 'Agosto' "
				+ "WHEN 9 THEN 'Setembro' WHEN 10 THEN 'Outubro' WHEN 11 THEN 'Novembro' "
				+ "WHEN 12 THEN 'Dezembro' END) AS mes, SUM(fin.valor) AS total FROM "
				+ "financeiro AS fin WHERE fin.tipo = '1' "
				+ "AND fin.status='1' And year(fin.data) = year(NOW()) AND fin.empresa_id = :paramID "
				+ "GROUP BY mes ORDER BY fin.data ASC ").setParameter("paramID", id).getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoLancamentoMensalDespesa(Long id) {
		List<Object[]> lista = em.createNativeQuery("SELECT (CASE MONTH(fin.data)  WHEN 1 THEN 'Janeiro' "
				+ "WHEN 2 THEN 'Fevereiro' WHEN 3 THEN 'Março' WHEN 4 THEN 'Abril' "
				+ "WHEN 5 THEN 'Maio' WHEN 6 THEN 'Junho' WHEN 7 THEN 'Julho' WHEN 8 THEN 'Agosto' "
				+ "WHEN 9 THEN 'Setembro' WHEN 10 THEN 'Outubro' WHEN 11 THEN 'Novembro' "
				+ "WHEN 12 THEN 'Dezembro' END) AS mes, SUM(fin.valor) AS total FROM "
				+ "financeiro AS fin WHERE fin.tipo = '0' " + "AND fin.status='1' AND fin.empresa_id = :paramID "
				+ "GROUP BY mes ORDER BY fin.data ASC ").setParameter("paramID", id).getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoCategoria(Long id) {
		List<Object[]> lista = em
				.createNativeQuery(
						"SELECT " + "cat.descricao as categoria, COUNT(fin.categoria_financeiro_id) AS total " + "FROM "
								+ "financeiro AS fin " + "INNER JOIN "
								+ "categoria_financeiro AS cat ON (cat.id = fin.categoria_financeiro_id) " + "WHERE "
								+ "MONTH(fin.data) = MONTH(NOW()) " + "AND YEAR(fin.data) = YEAR(NOW()) "
								+ "AND fin.status = '1' " + "AND fin.empresa_id = :paramID " + "GROUP BY cat.descricao")
				.setParameter("paramID", id).getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoServicos(Long id) {
		List<Object[]> lista = em.createNativeQuery("SELECT " + "serv.descricao as servico, (CASE "
				+ "WHEN fin.servico_id ='26' THEN " + "(count(fin.servico_id='26') - count(fin.servico_id='26')) "
				+ "ELSE count(fin.servico_id) END) AS total " + "FROM financeiro AS fin "
				+ "INNER JOIN servico AS serv ON (serv.id = fin.servico_id) " + "WHERE MONTH(fin.data) = MONTH(NOW()) "
				+ "AND YEAR(fin.data) = YEAR(NOW()) " + "AND fin.status = '1' " + "AND fin.empresa_id = :paramID "
				+ "GROUP BY serv.descricao").setParameter("paramID", id).getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoFrmPagamento(Long id) {
		List<Object[]> lista = em.createNativeQuery("SELECT "
				+ "frm.descricao as formaPagamento, COUNT(fin.forma_pagamento_id) " + "AS total " + "FROM "
				+ "financeiro AS fin " + "INNER JOIN " + "forma_pagamento as frm ON (frm.id = fin.forma_pagamento_id) "
				+ "WHERE " + "MONTH(fin.data) = MONTH(NOW()) " + "AND fin.status='1' "
				+ "AND YEAR(fin.data) = YEAR(NOW()) AND fin.empresa_id = :paramID " + "GROUP BY frm.descricao")
				.setParameter("paramID", id).getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoCivil(Long id) {
		List<Object[]> lista = em.createNativeQuery("SELECT " + "ec.descricao, " + "(CASE "
				+ "WHEN ec.id = '8' THEN (COUNT(ec.id = '8') - COUNT(ec.id = '8')) " + "ELSE COUNT(ec.descricao) "
				+ "END) AS total " + "FROM " + "financeiro AS fin " + "INNER JOIN "
				+ "aluno AS al ON (al.id = fin.aluno_id) " + "INNER JOIN "
				+ "estado_civil AS ec ON (ec.id = al.estado_civil_id) " + "WHERE " + "MONTH(fin.data) = MONTH(NOW()) "
				+ "AND YEAR(fin.data) = YEAR(NOW()) " + "AND fin.status = '1' " + "AND fin.empresa_id = :paramID "
				+ "GROUP BY ec.descricao")
				.setParameter("paramID", id).getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoMasculino(Long id) {
		List<Object[]> listar = em.createNativeQuery("SELECT (CASE MONTH(al.data)  WHEN 1 THEN 'Janeiro' " 
				 + "WHEN 2 THEN 'Fevereiro' WHEN 3 THEN 'Março' WHEN 4 THEN 'Abril' "
				 + "WHEN 5 THEN 'Maio' WHEN 6 THEN 'Junho' WHEN 7 THEN 'Julho'   WHEN 8 THEN 'Agosto' " 
				 + "WHEN 9 THEN 'Setembro' WHEN 10 THEN 'Outubro'   WHEN 11 THEN 'Novembro' "
				 + "WHEN 12 THEN 'Dezembro' END) AS mes, COUNT(al.sexo_id) AS total FROM "
				 + "aluno AS al WHERE "
				 + "al.empresa_id = :paramID AND al.sexo_id = '4'   "
				 + "GROUP BY mes order by al.data asc")
				.setParameter("paramID", id).getResultList();

		return listar;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoFeminino(Long id) {
		List<Object[]> listar = em.createNativeQuery("SELECT   (CASE MONTH(al.data)  WHEN 1 THEN 'Janeiro' " 
				 + "WHEN 2 THEN 'Fevereiro' WHEN 3 THEN 'Março' WHEN 4 THEN 'Abril' "
				 + "WHEN 5 THEN 'Maio' WHEN 6 THEN 'Junho' WHEN 7 THEN 'Julho'   WHEN 8 THEN 'Agosto' " 
				 + "WHEN 9 THEN 'Setembro' WHEN 10 THEN 'Outubro'   WHEN 11 THEN 'Novembro' "
				 + "WHEN 12 THEN 'Dezembro' END) AS mes, COUNT(al.sexo_id) AS total FROM "
				 + "aluno AS al WHERE "
				 + "al.empresa_id = :paramID AND al.sexo_id = '5' "
				 + "GROUP BY mes order by al.data asc")
				.setParameter("paramID", id).getResultList();

		return listar;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> graficoUsuario(Long id) {
		List<Object[]> listar = em
				.createNativeQuery("SELECT " + "us.login,count(fin.usuario_id) as total, "
						+ "format(sum(fin.valor),2,'de_DE') as valor " + "FROM " + "financeiro AS fin " + "INNER JOIN "
						+ "usuario AS us ON (us.id = fin.usuario_id) " + "WHERE "
						+ "fin.empresa_id = :paramID group by us.login")
				.setParameter("paramID", id).getResultList();

		return listar;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> crossTab(Long id) {
		List<Object[]> listar = em
				.createNativeQuery("SELECT " 
						    + "COALESCE(cat.descricao, 'SUBTOTAL') AS categoria, "
							+ "SUM(IF(MONTH(fin.data) = 1, valor, 0)) AS 'Janeiro', "
							+ "SUM(IF(MONTH(fin.data) = 2, valor, 0)) AS 'Fevereiro', "
							+ "SUM(IF(MONTH(fin.data) = 3, valor, 0)) AS 'Marco', "
							+ "SUM(IF(MONTH(fin.data) = 4, valor, 0)) AS 'Abril', "
							+ "SUM(IF(MONTH(fin.data) = 5, valor, 0)) AS 'Maio', "
							+ "SUM(IF(MONTH(fin.data) = 6, valor, 0)) AS 'Junho', "
							+ "SUM(IF(MONTH(fin.data) = 7, valor, 0)) AS 'Julho', "
							+ "SUM(IF(MONTH(fin.data) = 8, valor, 0)) AS 'Agosto', "
							+ "SUM(IF(MONTH(fin.data) = 9, valor, 0)) AS 'Setembro', "
							+ "SUM(IF(MONTH(fin.data) = 10, valor, 0)) AS 'Outubro', "
							+ "SUM(IF(MONTH(fin.data) = 11, valor, 0)) AS 'Novembro', "
							+ "SUM(IF(MONTH(fin.data) = 12, valor, 0)) AS 'Dezembro', "
							+ "SUM(fin.valor) AS total_produto "
							+ "FROM "
							+ "financeiro AS fin "
							+ "INNER JOIN "
							+ "categoria_financeiro AS cat ON (fin.categoria_financeiro_id = cat.id) "
							+ "WHERE "
							+ "fin.tipo = '1' AND fin.status = '1' "
							+ "AND fin.empresa_id = :paramID "
							+ "GROUP BY cat.descricao WITH ROLLUP")
							.setParameter("paramID", id).getResultList();
	
							return listar;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> crossTabServicos(Long id) {
		List<Object[]> listar = em
				.createNativeQuery("SELECT  serv.valor as vl, "
						     + "COALESCE(serv.descricao , 'SUBTOTAL') AS servicos, " 
							 + "SUM(IF(MONTH(fin.data) = 1, fin.valor, 0)) AS 'Janeiro', " 
							 + "SUM(IF(MONTH(fin.data) = 2, fin.valor, 0)) AS 'Fevereiro', " 
							 + "SUM(IF(MONTH(fin.data) = 3, fin.valor, 0)) AS 'Marco', "
							 + "SUM(IF(MONTH(fin.data) = 4, fin.valor, 0)) AS 'Abril', "
							 + "SUM(IF(MONTH(fin.data) = 5, fin.valor, 0)) AS 'Maio', "
							 + "SUM(IF(MONTH(fin.data) = 6, fin.valor, 0)) AS 'Junho', "
							 + "SUM(IF(MONTH(fin.data) = 7, fin.valor, 0)) AS 'Julho', "
							 + "SUM(IF(MONTH(fin.data) = 8, fin.valor, 0)) AS 'Agosto', "
							 + "SUM(IF(MONTH(fin.data) = 9, fin.valor, 0)) AS 'Setembro'," 
							 + "SUM(IF(MONTH(fin.data) = 10, fin.valor, 0)) AS 'Outubro', "
							 + "SUM(IF(MONTH(fin.data) = 11, fin.valor, 0)) AS 'Novembro', "
							 + "SUM(IF(MONTH(fin.data) = 12, fin.valor, 0)) AS 'Dezembro', "
							 + "SUM(fin.valor) AS total_produto "
							 + "FROM "
							 + "financeiro AS fin " 
							 + "INNER JOIN "
							 + "aluno AS al ON (fin.aluno_id = al.id) "
                             + "INNER JOIN "
							 + "servico AS serv ON (serv.id = al.servico_id) "
							 + "WHERE "
							 + "fin.tipo = '1' AND fin.status = '1' " 
							 + "AND fin.empresa_id = :paramID "
							 + "GROUP BY serv.descricao WITH ROLLUP")
							.setParameter("paramID", id).getResultList();
	
							return listar;
	}

}
