package com.java.controller.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Empresa;
import com.java.modelo.Usuario;
import com.java.service.EmpresaService;
import com.java.service.UsuarioService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario;

	@Inject
	private EmpresaService empresaService;

	private Map<String, Empresa> listarEmpresas = new HashMap<String, Empresa>();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void init() throws IOException {

		this.limpar();
		preencheListasSelect();

	}

	private void preencheListasSelect() {
		
		String idAdmin  = Session.retornaUsuarioAdministradorGeral();

		if (idAdmin.equals("D")) {
			
			listarEmpresas = new TreeMap<String, Empresa>();
			for (Empresa empresa : empresaService.listarTodos()) {
				listarEmpresas.put(empresa.getNome(), empresa);
			}
			

			
		} else {
	
			Long idUsuario = Session.retornaIdUsuarioLogado();
			listarEmpresas = new TreeMap<String, Empresa>();
			for (Empresa empresa : empresaService.listarTodosEmpresa(idUsuario)) {
				listarEmpresas.put(empresa.getNome(), empresa);
			}
			
		}
	}

	public void salvar() {
		try {
			this.usuarioService.salvar(usuario);
			FacesUtil.addSuccessMessage("Usuário salvo com sucesso!");
			this.limpar();
		} catch (NegocioException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.usuario = new Usuario();
	}

	public Map<String, Empresa> getListarEmpresas() {
		return listarEmpresas;
	}

	public void setListarEmpresas(Map<String, Empresa> listarEmpresas) {
		this.listarEmpresas = listarEmpresas;
	}

}
