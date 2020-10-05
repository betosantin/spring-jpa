package br.com.robertosantin.spring.jpa.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {

	private Integer cliente;
	private BigDecimal total;
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
