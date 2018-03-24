package com.java.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.java.modelo.Usuario;



public class Session {
	
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
    }

	public static HttpSession getSession() {
        return (HttpSession) getFacesContext().getExternalContext().getSession(false);
    }
	
	public static Long retornaIdUsuarioLogado() {
		
		Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
		
		if (usuario != null) {
			return usuario.getId();
		}else {
			return null;
		}
	}
	
public static Long retornaIdEmpresa() {
		
		Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
		
		if (usuario != null) {
			return usuario.getEmpresa().getId();
		}else {
			return null;
		}
	}
	
	public static String retornaNomeCompletoUsuarioLogado() {
		
		Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
		
		if (usuario != null) {
			return usuario.getNomeCompleto();
		}else {
			return null;
		}
	}
	
	
public static String retornaUsuarioAdministradorGeral() {
		
		Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
		
		if (usuario != null) {
			return usuario.getAdmin();
		}else {
			return null;
		}
	}
	
public static String retornaLoginUsuarioLogado() {
		
		Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
		
		if (usuario != null) {
			return usuario.getLogin();
		}else {
			return null;
		}
	}

public static String retornaImagemEmpresa() {
	
	Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
	
	if (usuario != null) {
		return usuario.getEmpresa().getLogo();
	}else {
		return null;
	}
}


public static String retornaNomeEmpresaLog() {
	
	Usuario usuario = (Usuario) getSession().getAttribute("usuarioLogado");
	
	if (usuario != null) {
		return usuario.getEmpresa().getNome();
	}else {
		return null;
	}
}

	
}
