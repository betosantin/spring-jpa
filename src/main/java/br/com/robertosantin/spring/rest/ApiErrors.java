package br.com.robertosantin.spring.rest;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
	
	private List<String> erros;
	
	
	public ApiErrors(List<String> erros) {
		this.erros = erros;
	}

	public ApiErrors(String erro) {
		this.erros = Arrays.asList(erro);
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
}
