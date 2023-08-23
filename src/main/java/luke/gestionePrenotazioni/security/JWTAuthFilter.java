package luke.gestionePrenotazioni.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import luke.gestionePrenotazioni.entities.User;
import luke.gestionePrenotazioni.exceptions.UnauthorizedException;
import luke.gestionePrenotazioni.service.UsersService;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	private final JWTTools jwt;
	private final UsersService srv;

	@Autowired
	public JWTAuthFilter(JWTTools jwt, UsersService srv) {
		this.jwt = jwt;
		this.srv = srv;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer"))
			throw new UnauthorizedException("Per favore passa il token nell'authorization header");

		String token = authHeader.substring(7);
		System.out.println("Token ->" + token);

		jwt.verifyToken(token);

		String id = jwt.extractSubject(token);
		User currentUser = srv.findById(UUID.fromString(id));

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(currentUser, null,
				currentUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		System.out.println(request.getServletPath());
		return new AntPathMatcher().match("auth/**", request.getServletPath());
	}

}
