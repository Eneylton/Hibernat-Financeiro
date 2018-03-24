package com.java.controller.financeiro;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.CategoriaFinanceiro;
import com.java.modelo.Financeiro;
import com.java.modelo.FormaPagamento;
import com.java.relatorio.RelatorioService;
import com.java.service.AlunoService;
import com.java.service.CategoriaFinanceiroService;
import com.java.service.EmpresaService;
import com.java.service.FinanceiroService;
import com.java.service.FormaPagamentoService;
import com.java.service.ServicoService;
import com.java.service.UsuarioService;
import com.java.util.Extenso;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class FinanceiroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FinanceiroService financeiroService;

	@Inject
	private CategoriaFinanceiroService categoriaFinanceiroService;

	@Inject
	private FormaPagamentoService formaPagamentoService;

	@Inject
	private AlunoService alunoService;

	@Inject
	private EmpresaService empresaService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ServicoService servicoService;

	@Inject
	private RelatorioService relatorioService;

	private List<Financeiro> listarFinanceiros;

	private Financeiro financeiroEdicao = new Financeiro();

	private Map<String, CategoriaFinanceiro> listarCategorias = new HashMap<String, CategoriaFinanceiro>();

	private Map<String, FormaPagamento> listarFormaPagamento = new HashMap<String, FormaPagamento>();

	private Financeiro financeiroSelecionada;

	private Financeiro financeiro;

	private double lancamento;

	private double movimentacaoBancaria;

	private double despesasEmpresa;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		preencheListasSelect();
		totalEmpresaReceita();
		totalMovimentacaoBancaria();
		totalDespesas();
		limpar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarFinanceiros = financeiroService.listarPrevisao(idEmpresa);

	}

	public void totalEmpresaReceita() {
		Long idReceita = Session.retornaIdEmpresa();

		double receita = financeiroService.totalReceitaEmpresa(idReceita);
		double despesa = financeiroService.totalDespesaEmpresa(idReceita);

		this.lancamento = receita - despesa;

	}

	public void totalMovimentacaoBancaria() {
		Long idReceita = Session.retornaIdEmpresa();

		double receita = financeiroService.totalReceitaBanco(idReceita);
		double despesa = financeiroService.totalDespesaBanco(idReceita);

		this.movimentacaoBancaria = receita - despesa;

	}

	public void totalDespesas() {
		Long idReceita = Session.retornaIdEmpresa();

		this.despesasEmpresa = financeiroService.totalDespesaEmpresa(idReceita);

	}

	public void prepararNovoCadastro() {
		financeiroEdicao = new Financeiro();
	}

	private Date currentDate = new Date();

	public Date getCurrentDate() {
		return currentDate;
	}

	public void salvar() throws SQLException {
		try {

			Long idEmpresa = Session.retornaIdEmpresa();
			Long IdUsuario = Session.retornaIdUsuarioLogado();

			financeiroEdicao.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));
			financeiroEdicao.setUsuario(usuarioService.retornarUsuarioPorID(String.valueOf(IdUsuario)));

			financeiroEdicao.setAluno(alunoService.retornarAlunoPorID(1L));
			financeiroEdicao.setServico(servicoService.retornarServicoPorID(26L));
			financeiroEdicao.setDataPagamento(financeiroEdicao.getData());
			String valorExtenso = new Extenso(financeiroEdicao.getValor()).toString();
			financeiroEdicao.setValorExtenso(valorExtenso);

			this.financeiroService.salvar(financeiroEdicao);

			FacesUtil.addSuccessMessage("Laçamento registrado com Sucesso !!!!! ");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:total", "frm:financeiroTable"));

			consultar();
			totalEmpresaReceita();
			totalMovimentacaoBancaria();
			totalDespesas();
			limpar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			financeiroService.excluir(financeiroSelecionada);
			this.listarFinanceiros.remove(financeiroSelecionada);
			FacesUtil.addSuccessMessage("Lançamento excluído com sucesso.");

		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private void preencheListasSelect() throws SQLException {

		listarCategorias = new HashMap<String, CategoriaFinanceiro>();
		for (CategoriaFinanceiro categoriaFinan : categoriaFinanceiroService.listarTodos()) {
			listarCategorias.put(categoriaFinan.getDescricao(), categoriaFinan);
		}

		listarFormaPagamento = new TreeMap<String, FormaPagamento>();
		for (FormaPagamento formaPagamento : formaPagamentoService.listarTodos()) {
			listarFormaPagamento.put(formaPagamento.getDescricao(), formaPagamento);
		}

	}

	public void imprirRecibo() throws SQLException, JRException {
		
		Long idFinanceiro = this.financeiroSelecionada.getId();

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idFinanceiro", Integer.valueOf(idFinanceiro.toString()));

		listarFinanceiros = financeiroService.reciboLancamento(idFinanceiro);
		relatorioService.gerarReciboLancamento(listarFinanceiros, (HashMap<String, Object>) parametros, "ReciboLancamento");

		consultar();
		totalEmpresaReceita();
		totalMovimentacaoBancaria();
		totalDespesas();
		limpar();

	}
	
	public void faturamentoPDF() {

		try {
			Long idEmpresa = Session.retornaIdEmpresa();

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idEmpresa", idEmpresa);

			listarFinanceiros = financeiroService.reciboFaturamento(idEmpresa);
			relatorioService.gerarFaturamento(listarFinanceiros, (HashMap<String, Object>) parametros, "Faturamento");

			consultar();
			totalEmpresaReceita();
			totalMovimentacaoBancaria();
			totalDespesas();
			limpar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void limpar() {
		this.financeiro = new Financeiro();
	}

	public boolean isEditando() {

		return this.financeiroEdicao.getId() != null;

	}

	public boolean isOcultarInput() {

		return this.financeiroEdicao.getId() == null;

	}

	public List<Financeiro> getListarFinanceiros() {
		return listarFinanceiros;
	}

	public void setListarFinanceiros(List<Financeiro> listarFinanceiros) {
		this.listarFinanceiros = listarFinanceiros;
	}

	public Financeiro getFinanceiroEdicao() {
		return financeiroEdicao;
	}

	public void setFinanceiroEdicao(Financeiro financeiroEdicao) {
		this.financeiroEdicao = financeiroEdicao;
	}

	public Financeiro getFinanceiroSelecionada() {
		return financeiroSelecionada;
	}

	public void setFinanceiroSelecionada(Financeiro financeiroSelecionada) {
		this.financeiroSelecionada = financeiroSelecionada;
	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public double getLancamento() {
		return lancamento;
	}

	public void setLancamento(double lancamento) {
		this.lancamento = lancamento;
	}

	public double getMovimentacaoBancaria() {
		return movimentacaoBancaria;
	}

	public void setMovimentacaoBancaria(double movimentacaoBancaria) {
		this.movimentacaoBancaria = movimentacaoBancaria;
	}

	public double getDespesasEmpresa() {
		return despesasEmpresa;
	}

	public void setDespesasEmpresa(double despesasEmpresa) {
		this.despesasEmpresa = despesasEmpresa;
	}

	public Map<String, CategoriaFinanceiro> getListarCategorias() {
		return listarCategorias;
	}

	public void setListarCategorias(Map<String, CategoriaFinanceiro> listarCategorias) {
		this.listarCategorias = listarCategorias;
	}

	public Map<String, FormaPagamento> getListarFormaPagamento() {
		return listarFormaPagamento;
	}

	public void setListarFormaPagamento(Map<String, FormaPagamento> listarFormaPagamento) {
		this.listarFormaPagamento = listarFormaPagamento;
	}

}
