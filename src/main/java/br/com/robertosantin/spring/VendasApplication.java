package br.com.robertosantin.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication 
	extends SpringBootServletInitializer {
	//Para gerar o WAR
	
//	@Bean
//	public CommandLineRunner init(@Autowired ClienteRepository clientes, @Autowired PedidoRepository pedidos, @Autowired ProdutoRepository produtoRepository, @Autowired ItemPedidoRepository itemPedidoRepository, @Autowired UsuarioServiceImpl usuarioService) {
//		
//		return args -> {
//			clientes.save(new Cliente("Roberto S", "47667779058"));
//			clientes.save(new Cliente("Teste", "47667779058"));
//			
//			List<Cliente> clis = clientes.findAll();
//			clis.forEach(System.out::println);
//			
//			clis.forEach(c -> {
//				c.setNome(c.getNome() + " atualizado");
//				clientes.save(c);
//			});
//			
//			clis = clientes.findAll();
//			clis.forEach(System.out::println);
//			
//			
//			System.out.println("Buscando por nome....");
//			clis = clientes.findByNomeLike("%Roberto%");
//			clis.forEach(System.out::println);
//			
//			clis = clientes.findAll();
//			clis.forEach( c -> {
//				System.out.println(clientes.existsById(c.getId()));
//			});
//			
//			
//			clis = clientes.findAll();
//			clis.forEach(c -> {
//				//clientes.delete(c);
//			});
//			
//			clis = clientes.findAll();
//			if(clis.isEmpty()) {
//				System.out.println("Nenhum cliente encontrado.");
//			} else {
//				clis.forEach(System.out::println);
//			}
//			
//			Cliente c = clis.get(0);
//			
//			System.out.println("-------------Pedidos-------------");
//			
//			Pedido p = new Pedido();
//			p.setCliente(c);
//			p.setDataPedido(LocalDate.now());
//			p.setTotal(BigDecimal.valueOf(99.93));
//			
//			pedidos.save(p);
//			
//			Pedido p1 = new Pedido();
//			p1.setCliente(c);
//			p1.setDataPedido(LocalDate.now());
//			p1.setTotal(BigDecimal.valueOf(2.99));
//			p1.setStatus(StatusPedido.REALIZADO);
//			
//			Produto prod = new Produto();
//			prod.setDescricao("Chapeu");
//			prod.setPreco(new BigDecimal(2.99));
//			
//			produtoRepository.save(prod);
//			
//			pedidos.save(p1);
//			
//			ItemPedido item = new ItemPedido();
//			item.setPedido(p1);
//			item.setProduto(prod);
//			item.setQuantidade(1);
//			
//			itemPedidoRepository.save(item);
//			
//			p1.setItens(Arrays.asList(item));
//			
//			pedidos.save(p1);
//			
//			Cliente cli = clientes.findClienteFetchPedidos(c.getId());
//			
//			System.out.println(cli);
//			System.out.println(cli.getPedidos());
//			
//			
//			pedidos.findByCliente(cli).forEach(System.out::println);;
//			
//			Usuario usuario = new Usuario();
//			usuario.setAdmin(true);
//			usuario.setLogin("roberto");
//			usuario.setSenha("123");
//			
//			usuarioService.salvar(usuario);
//		};
//		
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
	
	
}
