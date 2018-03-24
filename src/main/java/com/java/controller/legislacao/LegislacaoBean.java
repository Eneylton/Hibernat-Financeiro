package com.java.controller.legislacao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.Aluno;
import com.java.modelo.CadastroAgenda2;
import com.java.service.AlunoService;
import com.java.service.CadastroAgenda2Service;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LegislacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroAgenda2Service cadastroAgendaService;

	@Inject
	private AlunoService alunoService;

	private Map<String, Aluno> listarAlunos = new HashMap<String, Aluno>();

	private List<CadastroAgenda2> ListarAlunosAgenda;

	private CadastroAgenda2 cadastroAgendaSelecionada;

	private CadastroAgenda2 cadastroAgenda = new CadastroAgenda2();

	private CadastroAgenda2 agenda;

	@PostConstruct
	public void init() throws SQLException, NumberFormatException, NegocioException {
		try {
			preencheListasSelect();
			consultar();
			limpar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public void salvar() throws SQLException {
		try {

			this.cadastroAgendaService.salvar(cadastroAgenda);

			FacesUtil.addSuccessMessage("Prova Agendada com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:cadastroAgendaTable"));
			
			limpar();
			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}
	
	public void editar(CadastroAgenda2 cadastroAgenda2) {
		try {
			this.cadastroAgendaService.editar(cadastroAgenda2);

			FacesUtil.addSuccessMessage("Prova de Legislação Atualizada com sucesso!");
			
			consultar();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}


	
	public void consultar() throws SQLException {

		ListarAlunosAgenda = cadastroAgendaService.listarTodos();

	}

	private void preencheListasSelect() {
		
		Long idUsuario  = Session.retornaIdUsuarioLogado();

		listarAlunos = new TreeMap<String, Aluno>();
		for (Aluno aluno : alunoService.listarTodosPorUsuario(idUsuario)) {
			listarAlunos.put(aluno.getNome(), aluno);
		}

	}

	public void limpar() {
		this.cadastroAgenda = new CadastroAgenda2();
	}

	public Map<String, Aluno> getListarAlunos() {
		return listarAlunos;
	}

	public void setListarAlunos(Map<String, Aluno> listarAlunos) {
		this.listarAlunos = listarAlunos;
	}

	public List<CadastroAgenda2> getListarAlunosAgenda() {
		return ListarAlunosAgenda;
	}

	public void setListarAlunosAgenda(List<CadastroAgenda2> listarAlunosAgenda) {
		ListarAlunosAgenda = listarAlunosAgenda;
	}

	public CadastroAgenda2 getCadastroAgendaSelecionada() {
		return cadastroAgendaSelecionada;
	}

	public void setCadastroAgendaSelecionada(CadastroAgenda2 cadastroAgendaSelecionada) {
		this.cadastroAgendaSelecionada = cadastroAgendaSelecionada;
	}

	public CadastroAgenda2 getCadastroAgenda() {
		return cadastroAgenda;
	}

	public void setCadastroAgenda(CadastroAgenda2 cadastroAgenda) {
		this.cadastroAgenda = cadastroAgenda;
	}

	public CadastroAgenda2 getAgenda() {
		return agenda;
	}

	public void setAgenda(CadastroAgenda2 agenda) {
		this.agenda = agenda;
	}

}
