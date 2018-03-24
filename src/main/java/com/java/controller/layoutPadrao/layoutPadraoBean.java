package com.java.controller.layoutPadrao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.java.util.Constantes;
import com.java.util.Session;
import com.java.util.jsfsecurity.SecurityUtil;

@Named
@ViewScoped
public class layoutPadraoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeUsuarioLogado;

	private boolean usuarioConectado;

	private String empresaLog;

	private String empresaImg;

	private String administrador;
	

	private Long idEmpresa;

	private String url;

	private String data;
	
	private Date dataAtual;

	@PostConstruct
	public void init() {
		this.nomeUsuarioLogado = Session.retornaNomeCompletoUsuarioLogado();
		this.administrador = Session.retornaUsuarioAdministradorGeral();
		this.empresaLog = Session.retornaNomeEmpresaLog();
		this.idEmpresa = Session.retornaIdEmpresa();
		this.empresaImg = Session.retornaImagemEmpresa();
		this.url = Constantes.URL;
		
		
		Locale local = new Locale("pt","BR");
		SimpleDateFormat sd = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",local);
		
		dataAtual = new Date();
		
		this.data = sd.format(dataAtual);

		if (SecurityUtil.isLoggedIn()) {
			this.setUsuarioConectado(true);
		} else {
			this.setUsuarioConectado(false);
		}

	}

	public String getNomeUsuarioLogado() {
		return nomeUsuarioLogado;
	}

	public void setNomeUsuarioLogado(String nomeUsuarioLogado) {
		this.nomeUsuarioLogado = nomeUsuarioLogado;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUsuarioConectado() {
		return usuarioConectado;
	}

	public void setUsuarioConectado(boolean usuarioConectado) {
		this.usuarioConectado = usuarioConectado;
	}

	public String getEmpresaLog() {
		return empresaLog;
	}

	public void setEmpresaLog(String empresaLog) {
		this.empresaLog = empresaLog;
	}

	public String getEmpresaImg() {
		return empresaImg;
	}

	public void setEmpresaImg(String empresaImg) {
		this.empresaImg = empresaImg;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}
	
	

}
