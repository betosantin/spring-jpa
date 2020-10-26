package br.com.robertosantin.spring.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.robertosantin.spring.jpa.entity.Usuario;
import br.com.robertosantin.spring.jpa.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return User.builder()
//				.username("roberto")
//				.password(encoder.encode("123"))
//				.roles("USER", "ADMIN").build();

		Usuario usuario = usuarioRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar sua Conta"));

		String[] roles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		return User.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

}
