package br.com.robertosantin.spring.jpa.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.robertosantin.spring.jpa.entity.Cliente;

@Transactional
@Repository
public class Clientes_JdbcTemplate {

	private static String INSERT = "INSERT INTO CLIENTE (NOME) VALUES (?) ";
	private static String UPDATE = "UPDATE CLIENTE SET NOME = ?  WHERE ID = ? ";
	private static String DELETE = "DELETE FROM CLIENTE WHERE ID = ? ";
	private static String SELECT_ALL = "SELECT * FROM CLIENTE ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Cliente salvar(Cliente cliente) {
		jdbcTemplate.update(INSERT, new Object[] { cliente.getNome() });

		return cliente;
	}

	public Cliente atualizar(Cliente cliente) {
		jdbcTemplate.update(UPDATE, new Object[] { cliente.getNome(), cliente.getId() });

		return cliente;
	}

	public void deletar(Cliente cliente) {
		this.deletar(cliente.getId());
	}

	public void deletar(Integer id) {
		jdbcTemplate.update(DELETE, new Object[] { id });
	}

	public List<Cliente> buscarPorNome(String nome) {
		return jdbcTemplate.query(SELECT_ALL.concat(" WHERE NOME like ?"), new Object[] { "%" + nome + "%" },
				clienteMapper());
	}

	public List<Cliente> obterTodos() {
		return jdbcTemplate.query(SELECT_ALL, clienteMapper());
	}

	private RowMapper<Cliente> clienteMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"));
			}
		};
	}
}
