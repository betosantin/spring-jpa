package br.com.robertosantin.spring.config.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.robertosantin.spring.VendasApplication;
import br.com.robertosantin.spring.jpa.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expericao}")
	private String expiracao;

	@Value("${security.jwt.chaveAssinatura}")
	private String chaveAssinatura;

	public String gerarToken(Usuario usuario) {
		long expiracao = Long.valueOf(this.expiracao);

		LocalDateTime dateTime = LocalDateTime.now().plusMinutes(expiracao);
		
		Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts.builder()
				.setSubject(usuario.getLogin())
				.setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, this.chaveAssinatura)
				.compact();
	}
	
	private Claims obterClaims(String token) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(this.chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean isTokenValido(String token) {
		
		try {
			Claims c = this.obterClaims(token);

			Date dateEx = c.getExpiration();

			LocalDateTime dateTime = dateEx.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			return !LocalDateTime.now().isAfter(dateTime);

		} catch (ExpiredJwtException e) {
			
		}
		
		return false;
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return this.obterClaims(token).getSubject();
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
		
		JwtService service = context.getBean(JwtService.class);
		
		Usuario u = new Usuario();
		u.setLogin("olamundo");
		
		String token = service.gerarToken(u);
		
		System.out.println(token);
		
		boolean tokenValido = service.isTokenValido(token);
		
		System.out.println("tolen é valido? " + tokenValido);
	}
}
