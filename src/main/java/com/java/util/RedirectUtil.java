package com.java.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

public class RedirectUtil {
	
	public static void ir(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}
	
}
