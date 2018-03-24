package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.java.modelo.Aluno;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class AlunoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Aluno retornarAlunoPorID(Long id) {

		@SuppressWarnings("unchecked")
		List<Aluno> listarAlunos = (List<Aluno>) em
				.createQuery("from Aluno a inner join fetch a.sexo " + "inner join fetch a.estadoCivil "
						+ "inner join fetch a.servico " + "where a.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarAlunos != null) {
			return listarAlunos.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Aluno> listarTodos() {
		return em.createQuery("from Aluno a ORDER BY a.id desc").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> listarTodosPorUsuario(Long idUsuario) {
		return em
				.createQuery("select a from Aluno a " 
		                + "inner join fetch a.usuario "
						+ "where usuario_id = :paramUsuario ORDER BY a.id desc")
				.setParameter("paramUsuario", idUsuario).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> listarTodosPorEmpresa(Long idEmpresa) {
		return em
				.createQuery("select a from Aluno a " + "inner join fetch a.empresa "
						+ "where empresa_id = :paramEmpresa ORDER BY a.id desc")
				.setParameter("paramEmpresa", idEmpresa).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> listarTodosPorEmpresaUsuario(Long idEmpresa, Long idUsuario) {
		return em
				.createQuery("select a from Aluno a " + "inner join fetch a.empresa "
						+ "where empresa_id = :paramEmpresa AND usuario_id = :paramIDUser ORDER BY a.id desc")
				.setParameter("paramEmpresa", idEmpresa).setParameter("paramIDUser", idUsuario).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> listarTodosAlunosEmpresa(Long idEmpresa) {
		return em
				.createNativeQuery(
						"Select  al.id, "
								+ "al.data, "
								+ "al.nome, "
								+ "al.sobrenome,"
								+ "al.dataNascimento, "
								+ "al.rg, "
								+ "al.cpf, "
								+ "al.fixo, "
								+ "al.wathsapp, "
								+ "al.email, "
								+ "al.endereco, "
								+ "al.numero, "
								+ "al.bairro, "
								+ "al.latitude, "
								+ "al.longitude, "
								+ "al.cep, "
								+ "al.cidade, "
								+ "al.estado, "
								+ "al.foto, "
								+ "al.enderecoCompleto, "
								+ "al.empresa_id, "
								+ "al.servico_id, "
								+ "al.sexo_id,"
								+ "al.usuario_id,"
								+ "al.estado_civil_id,"
								+ "emp.id AS idEmpresa, "
								+ "emp.razao, "
								+ "emp.cnpj, "
								+ "emp.nome AS empresa, "
								+ "emp.logo AS logo, "
								+ "serv.id AS idServico, "
								+ "serv.descricao AS servicos, "
								+ "serv.valor AS valorServico "
								+ "from aluno as al " 
								+ "INNER JOIN servico as serv On (al.servico_id = serv.id) "
								+ "INNER JOIN empresa as emp On (al.empresa_id = emp.id) "
								+ "Where al.empresa_id = :paramID", Aluno.class)
						.setParameter("paramID", idEmpresa).getResultList();
			}

	@Transactional
	public void salvar(Aluno aluno) {
		em.merge(aluno);
	}

	@Transactional
	public void excluir(Aluno aluno) throws NegocioException {
		Aluno excluir = em.find(Aluno.class, aluno.getId());
		em.remove(excluir);
		em.flush();
	}

}
