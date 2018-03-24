package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.Agenda2DAO;
import com.java.modelo.Agenda2;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Agenda2.class)
public class Agenda2Converter implements Converter {

	private Agenda2DAO agendaDAO;

	public Agenda2Converter() {
		this.agendaDAO = CDIServiceLocator.getBean(Agenda2DAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Agenda2 retorno = null;

		try {
			if (value != null) {
				retorno = this.agendaDAO.retornarAgenda2PorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Agenda2) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}