package com.java.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.java.modelo.Financeiro;

public class RelatorioGeralDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarPrevisao(Long id) {
		List<Financeiro> listaFinanceiro = em
				.createNativeQuery("SELECT " + "*,al.nome,cf.descricao " + "FROM " + "financeiro AS fin "
						+ "INNER JOIN " + "aluno AS al ON (fin.aluno_id = al.id) " + "INNER JOIN "
						+ "categoria_financeiro AS cf ON (fin.categoria_financeiro_id = cf.id) "
						+ " Where fin.empresa_id =:paramID ORDER BY fin.data asc", Financeiro.class)
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

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarConsultaGeral(Long idEmp, boolean idDestino, boolean idStatus, boolean idTipo,
			Date dataInicio, Date dataFim) {
		List<Financeiro> listaFinanceiro = em
				.createNativeQuery("SELECT " + "*,al.nome,cf.descricao " + "FROM " + "financeiro AS fin "
						+ "INNER JOIN " + "aluno AS al ON (fin.aluno_id = al.id) " + "INNER JOIN "
						+ "categoria_financeiro AS cf ON (fin.categoria_financeiro_id = cf.id) " + "WHERE "
						+ "fin.empresa_id =:paramID " + "AND fin.data BETWEEN :paramDataInicio AND :paramDataFim "
						+ "AND fin.destino =:paramDestino " + "AND fin.status =:paramStatus "
						+ "AND fin.tipo =:paramTipo ORDER BY fin.data asc", Financeiro.class)
				.setParameter("paramID", idEmp).setParameter("paramDataInicio", dataInicio, TemporalType.DATE)
				.setParameter("paramDataFim", dataFim, TemporalType.DATE).setParameter("paramDestino", idDestino)
				.setParameter("paramStatus", idStatus).setParameter("paramTipo", idTipo).getResultList();

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

	public double totalReceita(Long idEmp, boolean idDestino, boolean idStatus, Date dataInicio,
			Date dataFim) {
		Query query = em
				.createNativeQuery("SELECT IFNULL(sum(fin.valor),0) as totalReceita " + "FROM " + "financeiro AS fin "
						+ "WHERE " + "fin.empresa_id =:paramID "
						+ "AND fin.data BETWEEN :paramDataInicio AND :paramDataFim " + "AND fin.destino =:paramDestino "
						+ "AND fin.status =:paramStatus " + "AND fin.tipo ='1' ")
				.setParameter("paramID", idEmp).setParameter("paramDataInicio", dataInicio, TemporalType.DATE)
				.setParameter("paramDataFim", dataFim, TemporalType.DATE).setParameter("paramDestino", idDestino)
				.setParameter("paramStatus", idStatus);

		return (double) query.getSingleResult();

	}

	public double totalDespesa(Long idEmp, boolean idDestino, boolean idStatus, Date dataInicio,
			Date dataFim) {
		Query query = em
				.createNativeQuery("SELECT IFNULL(sum(fin.valor),0) as totalReceita " + "FROM " + "financeiro AS fin "
						+ "WHERE " + "fin.empresa_id =:paramID "
						+ "AND fin.data BETWEEN :paramDataInicio AND :paramDataFim " + "AND fin.destino =:paramDestino "
						+ "AND fin.status =:paramStatus " + "AND fin.tipo ='0' ")
				.setParameter("paramID", idEmp).setParameter("paramDataInicio", dataInicio, TemporalType.DATE)
				.setParameter("paramDataFim", dataFim, TemporalType.DATE).setParameter("paramDestino", idDestino)
				.setParameter("paramStatus", idStatus);

		return (double) query.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	public List<Financeiro> listarRelatorioGeral(Long idEmp, boolean idDestino, boolean idStatus, boolean idTipo,
			Date dataInicio, Date dataFim) {
		return em
				.createNativeQuery("SELECT fin.id," + "fin.data," + "fin.status," + "fin.tipo," + "fin.valor,"
						+ "fin.valorExtenso," + "fin.dataPagamento, " + "fin.destino, " + "serv.id as idServico, "
						+ "serv.descricao AS servico, " + "cf.id as idCategoria, " + "cf.descricao AS categoria, "
						+ "us.id as idUsuario, " + "us.nomeCompleto, " + "al.id as idAluno, " + "al.nome AS aluno, "
						+ "emp.id as idEmpresa, " + "emp.logo, " + "emp.nome AS empresa, " + "emp.cnpj, "
						+ "(r.totalReceita) - (d.totalDespesa) AS total "
						+ "FROM (SELECT IFNULL(SUM(resp.valor), 0) AS totalReceita FROM " + "financeiro AS resp "
						+ "WHERE resp.tipo = '1') AS r, " + "(SELECT IFNULL(SUM(desp.valor), 0) AS totalDespesa "
						+ "FROM financeiro AS desp WHERE desp.tipo = '0') AS d " + "INNER JOIN " + "financeiro AS fin "
						+ "INNER JOIN " + "empresa AS emp ON (emp.id = fin.empresa_id) " + "INNER JOIN "
						+ "servico AS serv ON (serv.id = fin.servico_id) " + "INNER JOIN "
						+ "aluno AS al ON (al.id = fin.aluno_id) " + "INNER JOIN "
						+ "usuario AS us ON (us.id = fin.usuario_id) " + "INNER JOIN "
						+ "categoria_financeiro AS cf ON (cf.id = fin.categoria_financeiro_id) " + "WHERE "
						+ "fin.empresa_id =:paramID " + "AND fin.data BETWEEN :paramDataInicio AND :paramDataFim "
						+ "AND fin.destino =:paramDestino " + "AND fin.status =:paramStatus "
						+ "AND fin.tipo =:paramTipo ")
				.setParameter("paramID", idEmp).setParameter("paramDataInicio", dataInicio, TemporalType.DATE)
				.setParameter("paramDataFim", dataFim, TemporalType.DATE).setParameter("paramDestino", idDestino)
				.setParameter("paramStatus", idStatus).setParameter("paramTipo", idTipo).getResultList();
	}

}
