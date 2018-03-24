package com.java.controller.professor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.Professor;
import com.java.service.EmpresaService;
import com.java.service.ProfessorService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProfessorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorService professorService;

	@Inject
	private EmpresaService empresaService;
	
	private List<Professor> listarProfessors;

	private Professor professorEdicao = new Professor();

	private Professor professorSelecionada;

	private Professor professor;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		limpar();

	}

	public void consultar() throws SQLException {
		
		Long idEmpresa = Session.retornaIdEmpresa();

		listarProfessors = professorService.listarTodosPorEmpresa(idEmpresa);

	}

	public void prepararNovoCadastro() {
		professorEdicao = new Professor();
	}

	public void salvar() throws SQLException {
		try {
			
			Long IdEmpresa = Session.retornaIdEmpresa();
			
			professorEdicao.setEmpresa(empresaService.retornarEmpresaPorID(IdEmpresa));

			this.professorService.salvar(professorEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:professorTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			professorService.excluir(professorSelecionada);
			this.listarProfessors.remove(professorSelecionada);
			FacesUtil.addSuccessMessage("Professor " + professorSelecionada.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.professor = new Professor();
	}

	public List<Professor> getListarProfessors() {
		return listarProfessors;
	}

	public void setListarProfessors(List<Professor> listarProfessors) {
		this.listarProfessors = listarProfessors;
	}

	public Professor getProfessorEdicao() {
		return professorEdicao;
	}

	public void setProfessorEdicao(Professor professorEdicao) {
		this.professorEdicao = professorEdicao;
	}

	public Professor getProfessorSelecionada() {
		return professorSelecionada;
	}

	public void setProfessorSelecionada(Professor professorSelecionada) {
		this.professorSelecionada = professorSelecionada;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
