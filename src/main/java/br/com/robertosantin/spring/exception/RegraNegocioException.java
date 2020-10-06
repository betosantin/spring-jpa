package br.com.robertosantin.spring.exception;

public class RegraNegocioException extends RuntimeException {
	
	private static final long serialVersionUID = -553449615750604660L;

	public RegraNegocioException(String mensagem) {
		super(mensagem);
	}

}
