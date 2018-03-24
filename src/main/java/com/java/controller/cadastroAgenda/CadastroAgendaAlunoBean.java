package com.java.controller.cadastroAgenda;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Agenda;
import com.java.modelo.CadastroAgenda;
import com.java.service.CadastroAgendaService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAgendaAlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroAgendaService cadastroAgendaService;

	private List<Agenda> listarAlunosAgenda;

	private CadastroAgenda cadastroAgenda;

	private CadastroAgenda agenda;

	@PostConstruct
	public void init() throws SQLException, NumberFormatException, NegocioException {
		try {

			Long idEmpresa = Session.retornaIdEmpresa();

			listarAlunosAgenda = cadastroAgendaService.listarPrevisao(idEmpresa);

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public CadastroAgenda getCadastroAgenda() {
		return cadastroAgenda;
	}

	public void setCadastroAgenda(CadastroAgenda cadastroAgenda) {
		this.cadastroAgenda = cadastroAgenda;
	}

	public CadastroAgenda getAgenda() {
		return agenda;
	}

	public void setAgenda(CadastroAgenda agenda) {
		this.agenda = agenda;
	}

	public List<Agenda> getListarAlunosAgenda() {
		return listarAlunosAgenda;
	}

	public void setListarAlunosAgenda(List<Agenda> listarAlunosAgenda) {
		this.listarAlunosAgenda = listarAlunosAgenda;
	}

}
