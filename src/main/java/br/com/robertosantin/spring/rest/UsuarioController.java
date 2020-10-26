package br.com.robertosantin.spring.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.robertosantin.spring.jpa.entity.Usuario;
import br.com.robertosantin.spring.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/rest/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private PasswordEncoder pass;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senha = pass.encode(usuario.getSenha());
		usuario.setSenha(senha);
		
		return usuarioService.salvar(usuario);
	}
	
}
