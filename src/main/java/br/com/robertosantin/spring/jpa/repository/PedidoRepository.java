package br.com.robertosantin.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByCliente(Cliente cliente); 
	
}
