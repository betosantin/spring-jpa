package br.com.robertosantin.spring.jpa.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

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

import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.repository.ClienteRepository;

@RestController
@RequestMapping("/rest/cliente")
public class ClienteRestNewController {

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
	public Cliente getClienteById(@PathVariable("id") Integer id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return clienteRepository.save(cliente);
	}	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCliente(@PathVariable("id") Integer id) {
		clienteRepository.findById(id).map(clienteDeletado -> {
			clienteRepository.delete(clienteDeletado);
			return Void.TYPE;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		clienteRepository.findById(id).map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clienteRepository.save(cliente);
			return clienteExistente;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping("/procurar")
	public List<Cliente> find(Cliente cliente){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example = Example.of(cliente, matcher);
		
		return  clienteRepository.findAll(example);
	}
}
