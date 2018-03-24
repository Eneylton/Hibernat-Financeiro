package com.java.controller.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Usuario;
import com.java.service.UsuarioService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	UsuarioService usuarioService;

	private List<Usuario> usuarios = new ArrayList<>();

	private Usuario usuarioSelecionado;

	@PostConstruct
	public void inicializar() throws IOException {
		
	   Long idEmpresa = Session.retornaIdEmpresa();

		usuarios = usuarioService.listarTodos(idEmpresa);

	}

	public void excluir() {
		try {
			usuarioService.excluir(usuarioSelecionado);
			this.usuarios.remove(usuarioSelecionado);
			FacesUtil.addSuccessMessage("Usuário " + usuarioSelecionado.getNomeCompleto() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (ParseException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
