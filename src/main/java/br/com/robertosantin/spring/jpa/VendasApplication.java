package br.com.robertosantin.spring.jpa;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.repository.ClienteRepository;
import br.com.robertosantin.spring.jpa.repository.PedidoRepository;

@SpringBootApplication
@RestController
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(@Autowired ClienteRepository clientes, @Autowired PedidoRepository pedidos) {
		
		return args -> {
			clientes.save(new Cliente("Roberto S"));
			clientes.save(new Cliente("Teste"));
			
			List<Cliente> clis = clientes.findAll();
			clis.forEach(System.out::println);
			
			clis.forEach(c -> {
				c.setNome(c.getNome() + " atualizado");
				clientes.save(c);
			});
			
			clis = clientes.findAll();
			clis.forEach(System.out::println);
			
			
			System.out.println("Buscando por nome....");
			clis = clientes.findByNomeLike("%Roberto%");
			clis.forEach(System.out::println);
			
			clis = clientes.findAll();
			clis.forEach( c -> {
				System.out.println(clientes.existsById(c.getId()));
			});
			
			
			clis = clientes.findAll();
			clis.forEach(c -> {
				//clientes.delete(c);
			});
			
			clis = clientes.findAll();
			if(clis.isEmpty()) {
				System.out.println("Nenhum cliente encontrado.");
			} else {
				clis.forEach(System.out::println);
			}
			
			Cliente c = clis.get(0);
			
			System.out.println("-------------Pedidos-------------");
			
			Pedido p = new Pedido();
			p.setCliente(c);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(99.93));
			
			pedidos.save(p);
			
			Pedido p1 = new Pedido();
			p1.setCliente(c);
			p1.setDataPedido(LocalDate.now());
			p1.setTotal(BigDecimal.valueOf(72.34));
			
			pedidos.save(p1);
			
			Cliente cli = clientes.findClienteFetchPedidos(c.getId());
			
			System.out.println(cli);
			System.out.println(cli.getPedidos());
			
			
			pedidos.findByCliente(cli).forEach(System.out::println);;
		};
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
	
	
}
