package br.com.robertosantin.spring.exception;

public class SenhaInvalidaException extends RuntimeException {
	
	private static final long serialVersionUID = 2562341210543774829L;

	public SenhaInvalidaException() {
		super("Senha inválida.");
	}

}
