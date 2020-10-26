package br.com.robertosantin.spring.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.robertosantin.spring.annotations.NotEmptyList;

public class PedidoDTO {

	@NotNull(message = "{campo.pedidodto.cliente}")
	private Integer cliente;
	@NotNull(message = "{campo.pedidodto.preco}")
	private BigDecimal total;
	@NotEmptyList(message = "{campo.pedidodto.itens}")
	private List<ItensPedidoDTO> itens;

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ItensPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItensPedidoDTO> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "PedidoDTO {cliente=" + cliente + ", total=" + total + ", itens=" + itens + "}";
	}

}
