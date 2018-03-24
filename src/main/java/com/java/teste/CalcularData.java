package com.java.teste;

import java.text.SimpleDateFormat;
import java.util.Date;

import groovyjarjarcommonscli.ParseException;

public class CalcularData {

	public static void main(String[] args) throws ParseException, java.text.ParseException {
		String a = "20120401";
		String b = "20120331";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date data1 = new Date(format.parse(a).getTime());
		Date data2 = new Date(format.parse(b).getTime());
		if(data1.after(data2)){
			System.out.println("Data: " + a + " é posterior à " + b);
		}else if(data1.before(data2)){
			System.out.println("Data: " + a + " é inferior à " + b);
		}else{
			System.out.println("Data: " + a + " é igual à " + b);
		}
	}

}
