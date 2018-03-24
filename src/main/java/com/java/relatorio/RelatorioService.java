package com.java.relatorio;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.java.modelo.Agenda;
import com.java.modelo.Aluno;
import com.java.modelo.Financeiro;
import com.java.modelo.Turma;
import com.java.util.Constantes;
import com.java.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	private HttpServletResponse response;
	private FacesContext context;
	private Connection con;

	public RelatorioService() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}

	public void gerarAlunoPDF(List<Aluno> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=Alunos.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}

	public void gerarMapaInstrutoresPDF(List<Agenda> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=MapaInstrutor.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}

	public void gerarMapaAlunosPDF(List<Agenda> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=MapaInstrutor.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}
	
	public void gerarReciboPDF(List<Financeiro> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=Recibo.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}

	
	
	public void gerarReciboLancamento(List<Financeiro> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=Recibo.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}

	
	public void gerarFaturamento(List<Financeiro> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=Recibo.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}


	public void gerarRelatorioGeral(List<Financeiro> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=Recibo.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}
	
	
	public void gerarListaPrencial(List<Turma> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {

		try {

			String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
					+ ".jrxml";

			JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

			response.setHeader("Content-disposition", "inline; filename=Recibo.pdf");

			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(print, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}



	private Connection getConexao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/db_hibernat_financeiro?autoReconnect=true",
					"root", "ti1234");
			return con;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
