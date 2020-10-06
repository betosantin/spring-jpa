package br.com.robertosantin.spring.jpa;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.entity.ItemPedido;
import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.entity.Produto;
import br.com.robertosantin.spring.jpa.enums.StatusPedido;
import br.com.robertosantin.spring.jpa.repository.ClienteRepository;
import br.com.robertosantin.spring.jpa.repository.ItemPedidoRepository;
import br.com.robertosantin.spring.jpa.repository.PedidoRepository;
import br.com.robertosantin.spring.jpa.repository.ProdutoRepository;

@SpringBootApplication
@RestController
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(@Autowired ClienteRepository clientes, @Autowired PedidoRepository pedidos, @Autowired ProdutoRepository produtoRepository, @Autowired ItemPedidoRepository itemPedidoRepository) {
		
		return args -> {
			clientes.save(new Cliente("Roberto S", "02139429095"));
			clientes.save(new Cliente("Teste", "02139429095"));
			
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
			p1.setTotal(BigDecimal.valueOf(2.99));
			p1.setStatus(StatusPedido.REALIZADO);
			
			Produto prod = new Produto();
			prod.setDescricao("Chapeu");
			prod.setPreco(new BigDecimal(2.99));
			
			produtoRepository.save(prod);
			
			pedidos.save(p1);
			
			ItemPedido item = new ItemPedido();
			item.setPedido(p1);
			item.setProduto(prod);
			item.setQuantidade(1);
			
			itemPedidoRepository.save(item);
			
			p1.setItens(Arrays.asList(item));
			
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
