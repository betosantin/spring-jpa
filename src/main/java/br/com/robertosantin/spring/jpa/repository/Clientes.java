package br.com.robertosantin.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.robertosantin.spring.jpa.entity.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer>{

	List<Cliente> findByNomeLike(String nome);

}
