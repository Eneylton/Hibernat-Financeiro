package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.VeiculoDAO;
import com.java.modelo.Veiculo;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Veiculo.class)
public class VeiculoConverter implements Converter {

	private VeiculoDAO veiculoDAO;

	public VeiculoConverter() {
		this.veiculoDAO = CDIServiceLocator.getBean(VeiculoDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Veiculo retorno = null;

		try {
			if (value != null) {
				retorno = this.veiculoDAO.retornarVeiculoPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Veiculo) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}