package br.com.robertosantin.spring.rest.dto;

public class ItensPedidoDTO {
	
	private Integer produto;
	private Integer quantidade;
	
	public Integer getProduto() {
		return produto;
	}
	public void setProduto(Integer produto) {
		this.produto = produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return "ItensPedidoDTO {produto=" + produto + ", quantidade=" + quantidade + "}";
	}
	
}
