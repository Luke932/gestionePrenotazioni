package luke.gestionePrenotazioni.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import luke.gestionePrenotazioni.entities.User;
import luke.gestionePrenotazioni.exceptions.UnauthorizedException;
import luke.gestionePrenotazioni.payloads.LoginSuccessfullPayload;
import luke.gestionePrenotazioni.payloads.NewUserPayload;
import luke.gestionePrenotazioni.payloads.UserLoginPayload;
import luke.gestionePrenotazioni.service.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UsersService srv;
	private final JWTTools jwt;
	private final PasswordEncoder pe;

	public AuthController(UsersService srv, JWTTools jwt, PasswordEncoder pe) {
		this.srv = srv;
		this.jwt = jwt;
		this.pe = pe;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User save(@RequestBody NewUserPayload body) {
		body.setPassword(pe.encode(body.getPassword()));
		User create = srv.create(body);
		return create;
	}

	public LoginSuccessfullPayload login(@RequestBody UserLoginPayload body) {
		User user = srv.findByEmail(body.getEmail());

		if (pe.matches(body.getPassword(), user.getPassword())) {
			String token = jwt.createToken(user);
			return new LoginSuccessfullPayload(token);
		} else {
			throw new UnauthorizedException("Credenziali non valide");
		}
	}
}
