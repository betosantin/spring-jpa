package br.com.robertosantin.spring.rest.dto;

import java.math.BigDecimal;

public class InformacoesItemDTO {

	private String descricao;
	private BigDecimal precoUnitario;
	private Integer quantidade;
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	@Override
	public String toString() {
		return "InformacoesItemDTO {descricao=" + descricao + ", precoUnitario=" + precoUnitario + ", quantidade="
				+ quantidade + "}";
	}
	
}
