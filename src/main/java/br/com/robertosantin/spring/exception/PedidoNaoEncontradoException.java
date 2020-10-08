package br.com.robertosantin.spring.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -6980003192234648354L;

	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado");
	}
	
}
