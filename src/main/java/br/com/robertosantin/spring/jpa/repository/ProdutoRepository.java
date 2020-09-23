package br.com.robertosantin.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.robertosantin.spring.jpa.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
