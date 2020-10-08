package br.com.robertosantin.spring.jpa.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.robertosantin.spring.exception.PedidoNaoEncontradoException;
import br.com.robertosantin.spring.exception.RegraNegocioException;
import br.com.robertosantin.spring.jpa.entity.Cliente;
import br.com.robertosantin.spring.jpa.entity.ItemPedido;
import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.entity.Produto;
import br.com.robertosantin.spring.jpa.enums.StatusPedido;
import br.com.robertosantin.spring.jpa.repository.ClienteRepository;
import br.com.robertosantin.spring.jpa.repository.ItemPedidoRepository;
import br.com.robertosantin.spring.jpa.repository.PedidoRepository;
import br.com.robertosantin.spring.jpa.repository.ProdutoRepository;
import br.com.robertosantin.spring.jpa.rest.dto.ItensPedidoDTO;
import br.com.robertosantin.spring.jpa.rest.dto.PedidoDTO;
import br.com.robertosantin.spring.jpa.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ItemPedidoRepository ItemPedidoRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO pedido) {

		Cliente cliente = this.clienteRepository.findById(pedido.getCliente())
				.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

		Pedido pe = new Pedido();
		pe.setTotal(pedido.getTotal());
		pe.setDataPedido(LocalDate.now());
		pe.setCliente(cliente);
		pe.setStatus(StatusPedido.REALIZADO);
			
		List<ItemPedido> itens = converterItens(pe, pedido.getItens());
		pedidoRepository.save(pe);
		
		ItemPedidoRepository.saveAll(itens);
		
		pe.setItens(itens);
		
		return pe;
	}

	private List<ItemPedido> converterItens(Pedido pedido, List<ItensPedidoDTO> listaItens) {
		if (listaItens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
		}

		return listaItens.stream().map(dto -> {
			Integer produtoId = dto.getProduto();

			Produto produto = produtoRepository.findById(produtoId)
					.orElseThrow(() -> new RegraNegocioException("Código produto inválido: " + produtoId));

			ItemPedido item = new ItemPedido();
			item.setQuantidade(dto.getQuantidade());
			item.setPedido(pedido);
			item.setProduto(produto);

			return item;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidoRepository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido statusPedido) {
		pedidoRepository.findById(id).map( ped -> {
			ped.setStatus(statusPedido);
			return pedidoRepository.save(ped);
		}).orElseThrow( () -> new PedidoNaoEncontradoException());
			
	}
	
}