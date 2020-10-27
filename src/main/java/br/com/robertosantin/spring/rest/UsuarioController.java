package br.com.robertosantin.spring.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.robertosantin.spring.config.security.jwt.JwtService;
import br.com.robertosantin.spring.exception.SenhaInvalidaException;
import br.com.robertosantin.spring.jpa.entity.Usuario;
import br.com.robertosantin.spring.rest.dto.CredenciaisDTO;
import br.com.robertosantin.spring.rest.dto.TokenDTO;
import br.com.robertosantin.spring.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/rest/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private PasswordEncoder pass;

	@Autowired
	private JwtService jwtService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senha = pass.encode(usuario.getSenha());
		usuario.setSenha(senha);

		return usuarioService.salvar(usuario);
	}

	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO cred) {

		Usuario user = new Usuario();
		user.setLogin(cred.getLogin());
		user.setSenha(cred.getSenha());

		try {

			UserDetails ud = this.usuarioService.autenticar(user);

			user = new Usuario();
			user.setLogin(ud.getUsername());
			user.setSenha(ud.getPassword());

			TokenDTO token = new TokenDTO();
			token.setLogin(ud.getUsername());
			token.setToken(jwtService.gerarToken(user));

			return token;
			
		} catch (SenhaInvalidaException | UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}


	}

}
