package br.com.robertosantin.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.robertosantin.spring.jpa.entity.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer>{

	//Busca por nome utilizando like
	List<Cliente> findByNomeLike(String nome);
	
	//Busca por nome utilizando like e order by nome
	List<Cliente> findByNomeLikeOrderByNome(String nome);
	
	//Busca por nome utilizando like ou por Id
	List<Cliente> findByNomeLikeOrId(String nome, Integer id);
	
	//Busca por nome utilizando like ou por Id e por fim, order by nome
	List<Cliente> findByNomeLikeOrIdOrderByNome(String nome, Integer id);
	
	//Busca por nome retornando só um registro
	//Cliente findOneNome(String nome);
	
	//Verificar se id existe
	boolean existsById(Integer id);
	
	//Injetando consulta com hql query
	@Query(value = " select c from cliente c where c.nome like :nome")
	List<Cliente> encontrarPorNome(@Param("nome") String nome);
	
	//Injetando consulta com sql nativo
	@Query(value = " select * from cliente where nome like '%:nome%'", nativeQuery = true)
	List<Cliente> encontrarPorNomeNativo(@Param("nome") String nome);
	
	//Injetando um delete com hql query
	@Query(value = " delete from cliente c where c.nome =:nome")
	@Modifying
	void deleteByNome(String nome);
}
