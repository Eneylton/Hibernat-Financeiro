package com.java.controller.estatistica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;

import com.java.modelo.EstatisticaTipo;
import com.java.modelo.EstatisticaUsuario;
import com.java.service.EstatisticaService;
import com.java.util.Session;

@Named
@ViewScoped
public class EstatisticaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaService estatisticaService;

	private List<Object[]> listarUsuario = new ArrayList<>();

	private List<EstatisticaUsuario> listarTotalUsuario = new ArrayList<>();

	private BubbleChartModel bubbleModel;

	private EstatisticaTipo tipo;

	@PostConstruct
	public void init() throws SQLException {

		limpar();
		consultar();

	}

	public void consultar() {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarUsuario = estatisticaService.graficoUsuario(idEmpresa);

		for (Object[] user : listarUsuario) {

			EstatisticaUsuario usuario = new EstatisticaUsuario();

			String label = user[0].toString();
			String resultado = String.valueOf(user[1]);
			String val = user[2].toString();
			

			usuario.setUsuario(label);
			usuario.setTotal(Integer.parseInt(resultado));
			usuario.setValor(val);

			listarTotalUsuario.add(usuario);
			
			createBubbleModels();

		}

	}
	
	 private void createBubbleModels(){
		 
		 bubbleModel = initBubbleModel();
		 
	        bubbleModel.setTitle("Produtividade ");
	        bubbleModel.setShadow(false);
	        bubbleModel.setBubbleGradients(true);
	        bubbleModel.setBubbleAlpha(1.0);
	        
	        bubbleModel.getAxis(AxisType.X).setLabel("Quantidade de Atendimento");
	        bubbleModel.getAxis(AxisType.X).setTickAngle(-10);
	        bubbleModel.setSeriesColors("0696bb,008e5b,8cbb26,F4E500,fdc60b,f18e1c,e32322,c4037d,6d398b,444e99,2a71b0");
	        Axis yAxis = bubbleModel.getAxis(AxisType.Y);
	        yAxis = bubbleModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(250);
	        yAxis.setTickAngle(-10);
	 }
	 
	 private BubbleChartModel initBubbleModel(){
	        BubbleChartModel model = new BubbleChartModel();
	       

			for (EstatisticaUsuario us: listarTotalUsuario) {
				
				model.add(new BubbleChartSeries(us.getUsuario(),us.getTotal(),130,us.getTotal()));

			}    
	         
	        return model;
	    }
	 

	public void limpar() {
		this.tipo = new EstatisticaTipo();
	}

	public List<Object[]> getListarUsuario() {
		return listarUsuario;
	}

	public void setListarUsuario(List<Object[]> listarUsuario) {
		this.listarUsuario = listarUsuario;
	}

	public List<EstatisticaUsuario> getListarTotalUsuario() {
		return listarTotalUsuario;
	}

	public void setListarTotalUsuario(List<EstatisticaUsuario> listarTotalUsuario) {
		this.listarTotalUsuario = listarTotalUsuario;
	}

	public BubbleChartModel getBubbleModel() {
		return bubbleModel;
	}

	public void setBubbleModel(BubbleChartModel bubbleModel) {
		this.bubbleModel = bubbleModel;
	}

	public EstatisticaTipo getTipo() {
		return tipo;
	}

	public void setTipo(EstatisticaTipo tipo) {
		this.tipo = tipo;
	}

}
