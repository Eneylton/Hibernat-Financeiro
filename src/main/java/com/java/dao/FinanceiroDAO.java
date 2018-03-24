package com.java.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.java.modelo.Financeiro;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class FinanceiroDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Financeiro retornarFinanceiroPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Financeiro> listarFinanceiros = (List<Financeiro>) em
				.createQuery("from Financeiro f " + "inner join fetch f.aluno " + "inner join fetch f.servico "
						+ "inner join fetch f.categoriaFinanceiro " + "inner join fetch f.formaPagamento "
						+ "where f.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarFinanceiros != null) {
			return listarFinanceiros.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarTodos() {
		return em.createQuery("from Financeiro f ").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarTodosPorAluno(Long id) {
		return em
				.createQuery(
						"select f from Financeiro f " + "inner join fetch f.aluno " + "where aluno_id = :paramAluno ")
				.setParameter("paramAluno", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarParcelaFinanceiro(Long id) {
		return em.createNativeQuery("SELECT " + "fin.id,fin.valor,fin.data,fin.valorExtenso, "
				+ "fp.descricao as formaPagamento,fin.status, " + "fin.parcelaExtenso, " + "al.nome as aluno, "
				+ "us.nomeCompleto, " + "serv.descricao, " + "emp.nome as empresa,emp.cnpj,emp.logo " + "FROM "
				+ "financeiro AS fin " + "INNER JOIN " + "empresa AS emp ON (fin.empresa_id = emp.id) " + "INNER JOIN "
				+ "aluno AS al ON (fin.aluno_id = al.id) " + "INNER JOIN "
				+ "servico as serv ON (serv.id = al.servico_id) " + "INNER JOIN "
				+ "usuario as us ON (us.id =  fin.usuario_id) " + "INNER JOIN "
				+ "forma_pagamento as fp ON (fin.forma_pagamento_id = fp.id) " + "WHERE fin.id = :paramID")
				.setParameter("paramID", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> reciboLancamento(Long id) {
		return em.createNativeQuery("SELECT *, " + "fp.descricao as formaPagamento, " + "al.nome as aluno, "
				+ "us.nomeCompleto, " + "serv.descricao as servico, " + "cf.descricao as categoria, "
				+ "emp.nome as empresa,emp.cnpj,emp.logo " + "FROM " + "financeiro AS fin " + "INNER JOIN "
				+ "empresa AS emp ON (fin.empresa_id = emp.id) " + "INNER JOIN "
				+ "aluno AS al ON (fin.aluno_id = al.id) " + "INNER JOIN "
				+ "servico as serv ON (serv.id = al.servico_id) " + "INNER JOIN "
				+ "usuario as us ON (us.id =  fin.usuario_id) " + "INNER JOIN "
				+ "forma_pagamento as fp ON (fin.forma_pagamento_id = fp.id) " + "INNER JOIN "
				+ "categoria_financeiro as cf ON (cf.id = fin.categoria_financeiro_id) " + "WHERE "
				+ "fin.id =:paramID ", Financeiro.class).setParameter("paramID", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> reciboFaturamento(Long id) {
		return em.createNativeQuery("SELECT "
				+ "*, emp.nome AS empresa, emp.cnpj, emp.logo,us.id as idUsuario,us.nomeCompleto, "
				+ "al.nome as aluno,(r.somaReceita) - (d.somaDespesa) AS faturamento, "
				+ "serv.descricao as servico,ct.descricao as categoria " + "FROM " + "(SELECT "
				+ "IFNULL(SUM(REPLACE(receita.valor, ',', '.')), 0) AS somaReceita " + "FROM "
				+ "financeiro AS receita " + "WHERE " + "receita.status = '1' " + "AND receita.tipo = '1' "
				+ "AND receita.destino = '0') AS r, "
				+ "(SELECT IFNULL(SUM(REPLACE(despesa.valor, ',', '.')), 0) AS somaDespesa " + "FROM "
				+ "financeiro AS despesa " + "WHERE " + "despesa.status = '1' " + "AND despesa.tipo = '0' "
				+ "AND despesa.destino = '0') AS d " + "INNER JOIN " + "financeiro AS fin " + "INNER JOIN "
				+ "empresa AS emp ON (emp.id = fin.empresa_id) " + "INNER JOIN "
				+ "usuario AS us ON (us.id = fin.usuario_id) " + "INNER JOIN "
				+ "aluno AS al ON (al.id = fin.aluno_id) " + "INNER JOIN "
				+ "servico AS serv ON (serv.id = fin.servico_id) " + "INNER JOIN "
				+ "categoria_financeiro AS ct ON (ct.id = fin.categoria_financeiro_id) " + "WHERE " + "emp.id =:paramID",
				Financeiro.class).setParameter("paramID", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarPrevisao(Long id) {
		List<Financeiro> listaFinanceiro = em
				.createNativeQuery("SELECT " + "*,al.nome,cf.descricao " + "FROM " + "financeiro AS fin "
						+ "INNER JOIN " + "aluno AS al ON (fin.aluno_id = al.id) " + "INNER JOIN "
						+ "categoria_financeiro AS cf ON (fin.categoria_financeiro_id = cf.id) "
						+ " Where fin.empresa_id =:paramID ORDER BY fin.id desc", Financeiro.class)
				.setParameter("paramID", id).getResultList();

		Calendar cal = new GregorianCalendar();

		Calendar cal2 = new GregorianCalendar();

		for (Financeiro fin : listaFinanceiro) {

			Date hoje = new Date();
			cal2.setTime(hoje);
			cal.setTime(fin.getData());
			Integer dia = cal.get(Calendar.DAY_OF_YEAR);
			Integer diaAtual = cal2.get(Calendar.DAY_OF_YEAR);
			Integer previsao = dia - diaAtual;

			if (previsao < 0) {

				fin.setPrevisao("7");

			} else {

				switch (previsao) {
				case 0:
					fin.setPrevisao("0");
					break;

				case 1:
					fin.setPrevisao("1");
					break;

				case 2:
					fin.setPrevisao("2");
					break;

				case 3:
					fin.setPrevisao("3");
					break;

				case 4:
					fin.setPrevisao("4");
					break;

				case 5:
					fin.setPrevisao("5");
					break;

				default:
					fin.setPrevisao("6");
					break;
				}

			}

		}
		return listaFinanceiro;
	}

	public double total(Long id) {
		Query query = em.createNativeQuery("select IFNULL(sum(fin.valor),0) as total from financeiro as fin "
				+ "inner join aluno as al ON (fin.aluno_id = al.id) " + "where fin.status ='0' AND al.id =:paramID")
				.setParameter("paramID", id);

		return (double) query.getSingleResult();

	}

	public double totalReceitaEmpresa(Long id) {
		Query query = em
				.createNativeQuery("SELECT IFNULL(sum(fin.valor),0) as totalReceita "
						+ "FROM financeiro as fin inner join empresa as emp On (fin.empresa_id = emp.id) "
						+ "where emp.id =:paramEmpresa AND fin.status = '1' AND fin.tipo = '1' AND fin.destino = '0'")
				.setParameter("paramEmpresa", id);

		return (double) query.getSingleResult();

	}

	public double totalDespesaEmpresa(Long id) {
		Query query = em
				.createNativeQuery("SELECT IFNULL(sum(fin.valor),0) as totalReceita "
						+ "FROM financeiro as fin inner join empresa as emp On (fin.empresa_id = emp.id) "
						+ "where emp.id =:paramEmpresa AND fin.status = '1' AND fin.tipo = '0' AND fin.destino = '0'")
				.setParameter("paramEmpresa", id);

		return (double) query.getSingleResult();

	}

	public double totalReceitaBanco(Long id) {
		Query query = em
				.createNativeQuery("SELECT IFNULL(sum(fin.valor),0) as totalReceita "
						+ "FROM financeiro as fin inner join empresa as emp On (fin.empresa_id = emp.id) "
						+ "where emp.id =:paramEmpresa AND fin.status = '1' AND fin.tipo = '1' AND fin.destino = '1'")
				.setParameter("paramEmpresa", id);

		return (double) query.getSingleResult();

	}

	public double totalDespesaBanco(Long id) {
		Query query = em
				.createNativeQuery("SELECT IFNULL(sum(fin.valor),0) as totalReceita "
						+ "FROM financeiro as fin inner join empresa as emp On (fin.empresa_id = emp.id) "
						+ "where emp.id =:paramEmpresa AND fin.status = '1' AND fin.tipo = '0' AND fin.destino = '1'")
				.setParameter("paramEmpresa", id);

		return (double) query.getSingleResult();

	}

	@Transactional
	public void salvar(Financeiro financeiro) {
		em.merge(financeiro);
	}

	@Transactional
	public void alterar(Financeiro financeiro) {
		em.merge(financeiro);
	}

	@Transactional
	public void excluir(Financeiro financeiro) throws NegocioException {
		Financeiro excluir = em.find(Financeiro.class, financeiro.getId());
		em.remove(excluir);
		em.flush();
	}

}
