package br.com.robertosantin.spring.rest;

import java.util.List;

import javax.validation.Valid;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/rest/cliente")
@Api("Api clientes")
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
	@ApiOperation("Obtem o cliente por id")
	@ApiResponses({
		@ApiResponse(code=202, message="Cliente encontrado"),
		@ApiResponse(code=404, message = "Cliente não encontrado para o ID informado")
	})
	public Cliente getClienteById(@PathVariable("id") @ApiParam("Id do cliente")  Integer id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Salva um novo cliente")
	@ApiResponses({
		@ApiResponse(code=201, message="Cliente salvo com sucesso"),
		@ApiResponse(code=400, message = "Erro de validação")
	})
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return clienteRepository.save(cliente);
	}	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Deletar o cliente por id")
	@ApiResponses({
		@ApiResponse(code=204, message="Cliente deletado com sucesso"),
		@ApiResponse(code=404, message = "Cliente não encontrado")
	})
	public void deleteCliente(@PathVariable("id") @ApiParam("Id do cliente") Integer id) {
		clienteRepository.findById(id).map(clienteDeletado -> {
			clienteRepository.delete(clienteDeletado);
			return Void.TYPE;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Atualizar o cliente por id")
	@ApiResponses({
		@ApiResponse(code=204, message="Cliente salvo com sucesso"),
		@ApiResponse(code=404, message = "Cliente não encontrado")
	})
	public void update(@PathVariable @ApiParam("Id do cliente")  Integer id, @RequestBody @ApiParam("Informações do cliente") @Valid Cliente cliente) {
		clienteRepository.findById(id).map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clienteRepository.save(cliente);
			return clienteExistente;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping("/procurar")
	@ApiOperation("Procurar o cliente")
	@ApiResponses({
		@ApiResponse(code=202, message="Cliente encontrado"),
		@ApiResponse(code=404, message = "Cliente não encontrado para as informações inseridas")
	})
	public List<Cliente> find(Cliente cliente){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example = Example.of(cliente, matcher);
		
		return  clienteRepository.findAll(example);
	}
}
