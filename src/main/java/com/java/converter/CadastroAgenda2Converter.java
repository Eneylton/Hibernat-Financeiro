package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.CadastroAgenda2DAO;
import com.java.modelo.CadastroAgenda;
import com.java.modelo.CadastroAgenda2;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = CadastroAgenda2.class)
public class CadastroAgenda2Converter implements Converter {

	private CadastroAgenda2DAO cadastroAgenda2DAO;

	public CadastroAgenda2Converter() {
		this.cadastroAgenda2DAO = CDIServiceLocator.getBean(CadastroAgenda2DAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		CadastroAgenda2 retorno = null;

		try {
			if (value != null) {
				retorno = this.cadastroAgenda2DAO.retornarCadastroAgenda2PorID(new Long(value));
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