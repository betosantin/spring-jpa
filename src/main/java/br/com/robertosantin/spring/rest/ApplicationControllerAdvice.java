package br.com.robertosantin.spring.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.robertosantin.spring.exception.PedidoNaoEncontradoException;
import br.com.robertosantin.spring.exception.RegraNegocioException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(RegraNegocioException regraNegocioException) {
		String msg = regraNegocioException.getMessage();

		return new ApiErrors(msg);
	}

	@ExceptionHandler(PedidoNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException encontradoException) {
		return new ApiErrors(encontradoException.getMessage());

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException argumentNotValidException) {
		List<String> erros = argumentNotValidException.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());
		
		return new ApiErrors(erros);
	}
}
