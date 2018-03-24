package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.EmpresaDAO;
import com.java.modelo.Empresa;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Empresa.class)
public class EmpresaConverter implements Converter {

	private EmpresaDAO empresaDAO;

	public EmpresaConverter() {
		this.empresaDAO = CDIServiceLocator.getBean(EmpresaDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Empresa retorno = null;

		try {
			if (value != null) {
				retorno = this.empresaDAO.retornarEmpresaPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Empresa) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}