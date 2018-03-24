package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.EstadoCivilDAO;
import com.java.modelo.EstadoCivil;
import com.java.util.cdi.CDIServiceLocator;


@FacesConverter(forClass = EstadoCivil.class)
public class EstadoCivilConverter implements Converter {

	private EstadoCivilDAO estadoCivilDAO;

	public EstadoCivilConverter() {
		this.estadoCivilDAO = CDIServiceLocator.getBean(EstadoCivilDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		EstadoCivil retorno = null;

		try {
			if (value != null) {
				retorno = this.estadoCivilDAO.retornarEstadoCivilPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((EstadoCivil) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}