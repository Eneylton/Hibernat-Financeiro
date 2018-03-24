package com.java.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.java.modelo.Usuario;
import com.java.modeloEspecializado.AlterarSenhaUsuario;
import com.java.util.NegocioException;
import com.java.util.jpa.Transactional;

public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Usuario retornarUsuarioPorID(Long id) {
		@SuppressWarnings("unchecked")
		List<Usuario> listarUsuarios = (List<Usuario>) em.createQuery(
				"from Usuario u inner join fetch u.empresa where u.id = :paramId ")
				.setParameter("paramId", id).getResultList();

		if (listarUsuarios != null) {
			return listarUsuarios.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarTodos(Long id) {

		return em.createQuery("from Usuario u Where empresa_id =:paramID")
				.setParameter("paramID", id)
				.getResultList();

	}

	@SuppressWarnings("unchecked")
	public Usuario retornaUsuarioPorLoginSenha(String login, String senha) {

		Usuario usuario;

		List<Usuario> consulta = em.createQuery("from Usuario where login = :paramLogin and senha = :paramSenha ")
				.setParameter("paramLogin", login).setParameter("paramSenha", senha).getResultList();

		if (consulta.size() > 0) {
			usuario = consulta.get(0);
		} else {
			usuario = null;
		}

		return usuario;

	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return em.merge(usuario);
	}

	@Transactional
	public void excluir(Usuario usuario) throws NegocioException {
		Usuario usuarioTemp = em.find(Usuario.class, usuario.getId());
		em.remove(usuarioTemp);
		em.flush();
	}

	@Transactional
	public void alterarSenha(AlterarSenhaUsuario alterarSenhaUsuario) {

		String sql = "update Usuario set senha = :senha where login = :login ";
		Query consulta = em.createNativeQuery(sql).setParameter("senha", alterarSenhaUsuario.getSenhaCriptografada())
				.setParameter("login", alterarSenhaUsuario.getLogin());
		consulta.executeUpdate();

	}

}
