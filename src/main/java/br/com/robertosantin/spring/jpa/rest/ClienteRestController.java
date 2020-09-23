package br.com.robertosantin.spring.jpa.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.repository.ClienteRepository;

@Controller
@RequestMapping("/rest/clientes")
public class ClienteRestController {

//	@RequestMapping(value="/hello/{nome}", 
//			method = RequestMethod.POST, 
//			consumes = {"application/json"}, 
//			produces = {"application/json"})
//	@ResponseBody
//	public String helloWork(@PathVariable("nome") String hello, @RequestBody Cliente cliente) {
//		return String.format("Hello %s", hello);
//	}

	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id) {
		Optional<Cliente> cli = clienteRepository.findById(id);

		if (cli.isPresent()) {
			return ResponseEntity.ok(cli.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<Cliente> postCliente(@RequestBody Cliente cliente) {
		Cliente cli = clienteRepository.save(cliente);

		if (cli != null) {
			return ResponseEntity.ok(cli);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") Integer id) {
		Optional<Cliente> cli = clienteRepository.findById(id);

		if (cli.isPresent()) {
			clienteRepository.delete(cli.get());
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente) {
		return clienteRepository.findById(id).map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clienteRepository.save(cliente);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/procurar")
	public ResponseEntity<List<Cliente>> find(Cliente cliente){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example = Example.of(cliente, matcher);
		List<Cliente> clientes = clienteRepository.findAll(example);
		
		return ResponseEntity.ok(clientes);
	}
}
