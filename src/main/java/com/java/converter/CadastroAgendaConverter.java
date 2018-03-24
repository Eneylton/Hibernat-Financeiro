package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.CadastroAgendaDAO;
import com.java.modelo.CadastroAgenda;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = CadastroAgenda.class)
public class CadastroAgendaConverter implements Converter {

	private CadastroAgendaDAO cadastroAgendaDAO;

	public CadastroAgendaConverter() {
		this.cadastroAgendaDAO = CDIServiceLocator.getBean(CadastroAgendaDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		CadastroAgenda retorno = null;

		try {
			if (value != null) {
				retorno = this.cadastroAgendaDAO.retornarCadastroAgendaPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((CadastroAgenda) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}