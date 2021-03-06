package br.com.robertosantin.spring.service;

import java.util.Optional;

import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.enums.StatusPedido;
import br.com.robertosantin.spring.rest.dto.PedidoDTO;

public interface PedidoService {
	
	Pedido salvar(PedidoDTO pedido);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);
	
	void atualizaStatus(Integer id, StatusPedido statusPedido);
	
}
