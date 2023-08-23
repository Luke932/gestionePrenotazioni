package luke.gestionePrenotazioni.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final JWTAuthFilter jwt;
	private final CorsFilter cf;

	@Autowired
	public SecurityConfig(JWTAuthFilter jwt, CorsFilter cf) {
		this.jwt = jwt;
		this.cf = cf;
	}

	@Bean
	SecurityFilterChain sfc(HttpSecurity http) throws Exception {
		// http.cors(c -> c.disable());
		http.csrf(c -> c.disable());

		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(a -> a.requestMatchers("/users/**").authenticated());
		http.authorizeHttpRequests(a -> a.requestMatchers("/edifici/**").authenticated());
		http.authorizeHttpRequests(a -> a.requestMatchers("/auth/**").permitAll());

		http.addFilterBefore(cf, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwt, CorsFilter.class);
		return http.build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
}
