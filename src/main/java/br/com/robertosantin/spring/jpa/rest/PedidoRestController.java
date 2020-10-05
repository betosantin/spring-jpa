package br.com.robertosantin.spring.jpa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.repository.PedidoRepository;

@RestController
@RequestMapping("/rest/pedido")
public class PedidoRestController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping("/{id}")
	public Pedido getPedidoById(@PathVariable("id") Integer id) {
		return pedidoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido postPedido(@RequestBody Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePedido(@PathVariable("id") Integer id) {
		pedidoRepository.findById(id).map(pedidoDeletado -> {
			pedidoRepository.delete(pedidoDeletado);
			return Void.TYPE;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody Pedido pedido) {
		pedidoRepository.findById(id).map(pedidoExistente -> {
			pedido.setId(pedidoExistente.getId());
			pedidoRepository.save(pedido);
			return pedidoExistente;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}
	
	@GetMapping("/procurar")
	public List<Pedido> find(Pedido pedido){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example = Example.of(pedido, matcher);
		
		return  pedidoRepository.findAll(example);
	}
}
