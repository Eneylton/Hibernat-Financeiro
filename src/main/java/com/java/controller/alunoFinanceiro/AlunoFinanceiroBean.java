package com.java.controller.alunoFinanceiro;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
public class AlunoFinanceiroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FinanceiroService financeiroService;

	@Inject
	private RelatorioService relatorioService;

	@Inject
	private AlunoService alunoService;

	@Inject
	private ServicoService servicoService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private EmpresaService empresaService;

	@Inject
	private FormaPagamentoService formaPagamentoService;

	@Inject
	private CategoriaFinanceiroService categoriaFinanceiroService;

	private Financeiro financeiro = new Financeiro();

	private Financeiro financeiroSelecionado;

	private List<Financeiro> listarParcelas = new ArrayList<Financeiro>();

	private Map<String, FormaPagamento> listarFormaPagamento = new HashMap<String, FormaPagamento>();

	private double total;

	private String msn;

	private String valorExtenso;

	@PostConstruct
	public void init() throws SQLException, NumberFormatException, NegocioException {
		preencheListasSelect();

		String idAluno = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("aluno");

		if (idAluno != null) {

			this.financeiro.setAluno(alunoService.retornarAlunoPorID(Long.parseLong(idAluno)));

			listarParcelas = financeiroService.listarTodosPorAluno(Long.parseLong(idAluno));
			restanteTotal();
		}

	}
	
	private void preencheListasSelect() {

		listarFormaPagamento = new TreeMap<String, FormaPagamento>();
		for (FormaPagamento formaPagamento : formaPagamentoService.listarTodos()) {
			listarFormaPagamento.put(formaPagamento.getDescricao(), formaPagamento);
		}
	}
	
	public void prepararNovoCadastro() {
	financeiro = new Financeiro();
	}

	public void calcularParcelas() throws NegocioException {

		Long idUser = Session.retornaIdUsuarioLogado();
		Long idEmpresa = Session.retornaIdEmpresa();

		int quantidade = financeiro.getQuantidade();

		double valorServico = financeiro.getAluno().getServico().getValor();

		double valorFornecido = financeiro.getValor();

		double resultado = (valorServico - valorFornecido);

		double valorParcelado = (resultado / quantidade);

		GregorianCalendar calendario = new GregorianCalendar();

		for (int i = 0; i <= quantidade; i++) {

			calendario.setTime(this.financeiro.getData());
			calendario.roll(GregorianCalendar.MONTH, i);

			Calendar dataPagamento = Calendar.getInstance();

			Date diaVencimento = new Date();
			Calendar diaAtual = Calendar.getInstance();
			diaAtual.setTime(diaVencimento);

			dataPagamento.setTime(this.financeiro.getData());
			dataPagamento.set(Calendar.MONTH, dataPagamento.get(Calendar.MONTH) + i);

			Long idAluno   = this.financeiro.getAluno().getId();
			Long idServico = this.financeiro.getAluno().getServico().getId();
			Long idFormaPg = this.financeiro.getFormaPagamento().getId();
			Long idCategoria = 1L;

			Financeiro finan = new Financeiro();
			if (i == 0) {
				finan.setQuantidade(i);
				finan.setParcelaExtenso("Entrada (+) ");
				finan.setValor(valorFornecido);
				this.valorExtenso = new Extenso(valorFornecido).toString();
				finan.setValorExtenso(valorExtenso);
				finan.setData(diaAtual.getTime());
				finan.setDataPagamento(diaAtual.getTime());
				finan.setAluno(alunoService.retornarAlunoPorID(idAluno));
				finan.setServico(servicoService.retornarServicoPorID(idServico));
				finan.setCategoriaFinanceiro(categoriaFinanceiroService.retornarCategoriaFinanceiroPorID(idCategoria));
				finan.setFormaPagamento(formaPagamentoService.retornarFormaPagamentoPorID(idFormaPg));
				finan.setDescricao(financeiro.getDescricao());
				finan.setUsuario(usuarioService.retornarUsuarioPorID(String.valueOf(new Long(idUser))));
				finan.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));
				finan.setStatus(true);
				finan.setTipo(true);
				listarParcelas.add(finan);
				financeiroService.salvar(finan);
				
				FacesUtil.addSuccessMessage("Entrada + Parcelas gerada com sucesso !!! ");
				
			} else {
				finan.setQuantidade(i);
				finan.setParcelaExtenso(i + " de " + quantidade);
				finan.setValor(valorParcelado);
				this.valorExtenso = new Extenso(valorParcelado).toString();
				finan.setValorExtenso(valorExtenso);
				finan.setData(dataPagamento.getTime());
				finan.setAluno(alunoService.retornarAlunoPorID(idAluno));
				finan.setServico(servicoService.retornarServicoPorID(idServico));
				finan.setCategoriaFinanceiro(categoriaFinanceiroService.retornarCategoriaFinanceiroPorID(idCategoria));
				finan.setFormaPagamento(formaPagamentoService.retornarFormaPagamentoPorID(idFormaPg));
				finan.setUsuario(usuarioService.retornarUsuarioPorID(String.valueOf(new Long(idUser))));
				finan.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));
				finan.setDestino(true);
				finan.setStatus(false);
				finan.setTipo(true);

				listarParcelas.add(finan);
				financeiroService.salvar(finan);

				Long idAl = this.financeiro.getAluno().getId();

				listarParcelas = financeiroService.listarTodosPorAluno(idAl);

				restanteTotal();

			}

		}

	}
	
	

	public void pagamentoAvista() throws NegocioException {

		Long idUser = Session.retornaIdUsuarioLogado();
		Long idEmpresa = Session.retornaIdEmpresa();

		double valorFornecido = financeiro.getAluno().getServico().getValor();

		int quantidade = 1;

		Long idAluno = this.financeiro.getAluno().getId();
		Long idServico = financeiro.getAluno().getServico().getId();
		Long idFormaPg = this.financeiro.getFormaPagamento().getId();
		Long idCategoria = 1L;

		for (int i = 1; i <= quantidade; i++) {

			Date d2 = new Date();
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(d2);

			Financeiro finan = new Financeiro();

			if (i == 1) {
				finan.setQuantidade(i);
				finan.setParcelaExtenso("Entrada (+) ");
				finan.setValor(valorFornecido);
				this.valorExtenso = new Extenso(valorFornecido).toString();
				finan.setValorExtenso(valorExtenso);
				finan.setData(cal2.getTime());
				finan.setDataPagamento(cal2.getTime());
				finan.setAluno(alunoService.retornarAlunoPorID(idAluno));
				finan.setServico(servicoService.retornarServicoPorID(idServico));
				finan.setCategoriaFinanceiro(categoriaFinanceiroService.retornarCategoriaFinanceiroPorID(idCategoria));
				finan.setFormaPagamento(formaPagamentoService.retornarFormaPagamentoPorID(idFormaPg));
				finan.setDescricao(financeiro.getDescricao());
				finan.setUsuario(usuarioService.retornarUsuarioPorID(String.valueOf(new Long(idUser))));
				finan.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));
				finan.setStatus(true);
				finan.setTipo(true);
				listarParcelas.add(finan);
				financeiroService.salvar(finan);

				FacesUtil.addSuccessMessage("Pagamento Realizado com Sucesso !!! ");

				Long idAl = this.financeiro.getAluno().getId();

				listarParcelas = financeiroService.listarTodosPorAluno(idAl);

				restanteTotal();

			}
		}
	}

	public void restanteTotal() {

		Long idALuno = financeiro.getAluno().getId();

		if (idALuno != null) {
			total = financeiroService.total(idALuno);
		}

	}

	public void editar(Financeiro financeiro) {
		try {

			Date diaVencimento = new Date();
			Calendar diaAtual = Calendar.getInstance();
			diaAtual.setTime(diaVencimento);
			financeiro.setDataPagamento(diaAtual.getTime());

			this.financeiroService.editar(financeiro);

			FacesUtil.addSuccessMessage("Pagamento Realizado com Sucesso !!!!");

			Long idAl = this.financeiro.getAluno().getId();

			listarParcelas = financeiroService.listarTodosPorAluno(idAl);

			restanteTotal();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void imprirReciboAluno() throws JRException {
		Long idFinanceiro = this.financeiroSelecionado.getId();

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idFinanceiro", Integer.valueOf(idFinanceiro.toString()));

		listarParcelas = financeiroService.listarParcelaFinanceiro(idFinanceiro);
		relatorioService.gerarReciboPDF(listarParcelas, (HashMap<String, Object>) parametros, "ReciboParcela");

		Long idAl = this.financeiro.getAluno().getId();

		listarParcelas = financeiroService.listarTodosPorAluno(idAl);

		restanteTotal();

	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public List<Financeiro> getListarParcelas() {
		return listarParcelas;
	}

	public void setListarParcelas(List<Financeiro> listarParcelas) {
		this.listarParcelas = listarParcelas;
	}

	public Financeiro getFinanceiroSelecionado() {
		return financeiroSelecionado;
	}

	public void setFinanceiroSelecionado(Financeiro financeiroSelecionado) {
		this.financeiroSelecionado = financeiroSelecionado;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getValorExtenso() {
		return valorExtenso;
	}

	public void setValorExtenso(String valorExtenso) {
		this.valorExtenso = valorExtenso;
	}

	public Map<String, FormaPagamento> getListarFormaPagamento() {
		return listarFormaPagamento;
	}

	public void setListarFormaPagamento(Map<String, FormaPagamento> listarFormaPagamento) {
		this.listarFormaPagamento = listarFormaPagamento;
	}

}
