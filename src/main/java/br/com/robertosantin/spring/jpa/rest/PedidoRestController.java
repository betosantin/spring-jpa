package br.com.robertosantin.spring.jpa.rest;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.robertosantin.spring.jpa.entity.ItemPedido;
import br.com.robertosantin.spring.jpa.entity.Pedido;
import br.com.robertosantin.spring.jpa.enums.StatusPedido;
import br.com.robertosantin.spring.jpa.rest.dto.AtualizaStatusDTO;
import br.com.robertosantin.spring.jpa.rest.dto.InformacoesItemDTO;
import br.com.robertosantin.spring.jpa.rest.dto.InformacoesPedidoDTO;
import br.com.robertosantin.spring.jpa.rest.dto.PedidoDTO;
import br.com.robertosantin.spring.jpa.service.PedidoService;

@RestController
@RequestMapping("/rest/pedido")
public class PedidoRestController {

	@Autowired
	private PedidoService pedidoRepository;

	@PostMapping("/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody PedidoDTO pedido) {
		return pedidoRepository.salvar(pedido).getId();
	}

	@GetMapping("/{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return pedidoRepository.obterPedidoCompleto(id).map(pedido -> converterParaDto(pedido))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id, @RequestBody AtualizaStatusDTO status) {
		
		pedidoRepository.atualizaStatus(id, StatusPedido.valueOf(status.getStatus()));
	}

	private InformacoesPedidoDTO converterParaDto(Pedido p) {
		InformacoesPedidoDTO in = new InformacoesPedidoDTO();
		in.setId(p.getId());
		in.setDataPedido(p.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		in.setCpf(p.getCliente().getCpf());
		in.setNomeCliente(p.getCliente().getNome());
		in.setTotal(p.getTotal());
		in.setItens(converterParaItens(p.getItens()));
		in.setStatus(p.getStatus().name());

		return in;
	}

	private List<InformacoesItemDTO> converterParaItens(List<ItemPedido> itens) {
		if (itens.isEmpty()) {
			return Collections.emptyList();
		}

		return itens.stream().map(item -> {
			InformacoesItemDTO in = new InformacoesItemDTO();
			in.setDescricao(item.getProduto().getDescricao());
			in.setPrecoUnitario(item.getProduto().getPreco());
			in.setQuantidade(item.getQuantidade());

			return in;
		}).collect(Collectors.toList());
	}
}
