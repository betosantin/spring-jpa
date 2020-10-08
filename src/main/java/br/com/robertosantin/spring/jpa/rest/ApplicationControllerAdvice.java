package br.com.robertosantin.spring.jpa.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.robertosantin.spring.exception.PedidoNaoEncontradoException;
import br.com.robertosantin.spring.exception.RegraNegocioException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(RegraNegocioException regraNegocioException){
		String msg = regraNegocioException.getMessage();
		
		return new ApiErrors(msg);
	}
	
	@ExceptionHandler(PedidoNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException encontradoException) {
		return new ApiErrors(encontradoException.getMessage());
		
	}
}
