package br.com.robertosantin.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.robertosantin.spring.config.security.jwt.JwtAuthFilter;
import br.com.robertosantin.spring.config.security.jwt.JwtService;
import br.com.robertosantin.spring.service.impl.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Autowired
	private JwtService jwtService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioServiceImpl);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("roberto")
//				.password(passwordEncoder().encode("123")).roles("USER").authorities("MANTER_USUARIO");
		
		auth
			.userDetailsService(usuarioServiceImpl)
			.passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		Caso não autenticado, vai para o formulario de autenticacao
//		http
//			.csrf().disable()
//			.authorizeRequests()
//				.antMatchers("/rest/cliente/**")
//				.authenticated()
//			.and()
//				.formLogin();
		
//		Utilizando basic authentication
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/rest/cliente/**")
			.authenticated()
			.antMatchers("/rest/usuario/**")
			.permitAll()
			.anyRequest().authenticated()
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");
	}

}
