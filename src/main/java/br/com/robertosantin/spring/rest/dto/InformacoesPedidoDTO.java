package br.com.robertosantin.spring.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class InformacoesPedidoDTO {
	
	
	private Integer id;
	private String cpf;
	private String nomeCliente;
	private BigDecimal total;
	private String dataPedido;
	private String status;
	private List<InformacoesItemDTO> itens;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public List<InformacoesItemDTO> getItens() {
		return itens;
	}
	public void setItens(List<InformacoesItemDTO> itens) {
		this.itens = itens;
	}
	
	public String getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "InformacoesPedidoDTO {id=" + id + ", cpf=" + cpf + ", nomeCliente=" + nomeCliente + ", total=" + total
				+ ", dataPedido=" + dataPedido + ", status=" + status + ", itens=" + itens + "}";
	}
	
}
