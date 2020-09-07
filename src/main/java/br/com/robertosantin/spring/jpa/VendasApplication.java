package br.com.robertosantin.spring.jpa;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.repository.Clientes;

@SpringBootApplication
@RestController
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes) {
		
		return args -> {
			clientes.salvar(new Cliente("Roberto S"));
			clientes.salvar(new Cliente("Teste"));
			
			List<Cliente> clis = clientes.obterTodos();
			clis.forEach(System.out::println);
			
			clis.forEach(c -> {
				c.setNome(c.getNome() + " atualizado");
				clientes.atualizar(c);
			});
			
			clis = clientes.obterTodos();
			clis.forEach(System.out::println);
			
			clis = clientes.obterTodos();
			clis.forEach(c -> {
				clientes.deletar(c);
			});
			
			clis = clientes.obterTodos();
			if(clis.isEmpty()) {
				System.out.println("Nenhum cliente encontrado.");
			} else {
				clis.forEach(System.out::println);
			}
			
		};
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
	
	
}
