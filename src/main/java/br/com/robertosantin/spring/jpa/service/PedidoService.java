package br.com.robertosantin.spring.jpa.service;

import java.util.Optional;

import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.rest.dto.PedidoDTO;

public interface PedidoService {
	
	Pedido salvar(PedidoDTO pedido);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);
	
}
