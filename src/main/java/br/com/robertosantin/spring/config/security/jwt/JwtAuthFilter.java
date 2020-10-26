package br.com.robertosantin.spring.config.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.robertosantin.spring.service.impl.UsuarioServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {

	JwtService jwtService;
	UsuarioServiceImpl usuarioServiceImpl;
	
	public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioServiceImpl) {
		super();
		this.jwtService = jwtService;
		this.usuarioServiceImpl = usuarioServiceImpl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String auth = request.getHeader("Authorization");
		
		if(auth != null && auth.startsWith("Bearer")) {
			String token = auth.split(" ")[1];
			
			if (jwtService.isTokenValido(token)) {
				String usuario = jwtService.obterLoginUsuario(token);

				UserDetails user = usuarioServiceImpl.loadUserByUsername(usuario);

				//Temos que criar um user UsernamePasswordAuthenticationToken e um 
				// WebAuthenticationDetailsSource para repassarmos ao contexto spring
				UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());

				u.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//Setamos no contexto spring o usuário autenticado
				SecurityContextHolder.getContext().setAuthentication(u);
			}
		}
			
		filterChain.doFilter(request, response);
	}

}
