package com.java.controller.turma;

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
import com.java.modelo.Professor;
import com.java.modelo.Turma;
import com.java.relatorio.RelatorioService;
import com.java.service.AlunoService;
import com.java.service.EmpresaService;
import com.java.service.ProfessorService;
import com.java.service.TurmaService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class TurmaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TurmaService turmaService;

	@Inject
	private EmpresaService empresaService;

	@Inject
	private RelatorioService relatorioServiice;

	@Inject
	private ProfessorService professorService;

	@Inject
	private AlunoService alunoService;

	private List<Turma> listarTurmas;

	private List<Turma> listarAlunosPorTurma;

	private Map<String, Professor> listarProfessores = new HashMap<String, Professor>();

	private Map<String, Aluno> listarAlunos = new HashMap<String, Aluno>();

	private Turma turmaEdicao = new Turma();

	private Turma turmaSelecionada;

	private Turma turma;

	private int limite;

	@PostConstruct
	public void init() throws SQLException {
		consultar();

		preencheListasSelect();
		limpar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarTurmas = turmaService.listarTodos(idEmpresa);

	}

	public void prepararNovoCadastro() {
		turmaEdicao = new Turma();
	}

	private void preencheListasSelect() {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarProfessores = new TreeMap<String, Professor>();

		for (Professor professor : professorService.listarTodosPorEmpresa(idEmpresa)) {
			listarProfessores.put(professor.getNome(), professor);
		}

		listarAlunos = new TreeMap<String, Aluno>();
		for (Aluno aluno : alunoService.listarTodosPorEmpresa(idEmpresa)) {
			listarAlunos.put(aluno.getNome(), aluno);
		}

	}

	public void buscarListaDeAlunos() {

		Long idTurma = turmaSelecionada.getId();

		turmaSelecionada = turmaService.buscarTurmaComAlunos(idTurma);
		
		List<Aluno> total22 = turmaSelecionada.getAlunos();
		
		int totalporTurma = total22.size();
	
        turmaSelecionada.setLimiteTotal(turmaSelecionada.getLimite() - totalporTurma);
        
	}

	public void imprimirListadePresenca() throws JRException, SQLException {

		Long idTurma = this.turmaSelecionada.getId();

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idTurma", idTurma);

		listarTurmas = turmaService.listarPresencaoAluno(idTurma);
		relatorioServiice.gerarListaPrencial(listarTurmas, (HashMap<String, Object>) parametros, "Turma");

		consultar();

	}

	public void salvar() throws SQLException {
		try {

			Long idEmpresa = Session.retornaIdEmpresa();

			turmaEdicao.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));

			this.turmaService.salvar(turmaEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:turmaTable"));

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			turmaService.excluir(turmaSelecionada);
			this.listarTurmas.remove(turmaSelecionada);
			FacesUtil.addSuccessMessage("Turma " + turmaSelecionada.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.turma = new Turma();
	}

	public List<Turma> getListarTurmas() {
		return listarTurmas;
	}

	public void setListarTurmas(List<Turma> listarTurmas) {
		this.listarTurmas = listarTurmas;
	}

	public Map<String, Professor> getListarProfessores() {
		return listarProfessores;
	}

	public void setListarProfessores(Map<String, Professor> listarProfessores) {
		this.listarProfessores = listarProfessores;
	}

	public Turma getTurmaEdicao() {
		return turmaEdicao;
	}

	public void setTurmaEdicao(Turma turmaEdicao) {
		this.turmaEdicao = turmaEdicao;
	}

	public Turma getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Turma turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Map<String, Aluno> getListarAlunos() {
		return listarAlunos;
	}

	public void setListarAlunos(Map<String, Aluno> listarAlunos) {
		this.listarAlunos = listarAlunos;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public List<Turma> getListarAlunosPorTurma() {
		return listarAlunosPorTurma;
	}

	public void setListarAlunosPorTurma(List<Turma> listarAlunosPorTurma) {
		this.listarAlunosPorTurma = listarAlunosPorTurma;
	}

}
