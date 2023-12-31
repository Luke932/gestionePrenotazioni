package luke.gestionePrenotazioni;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import luke.gestionePrenotazioni.controllers.EdificioController;
import luke.gestionePrenotazioni.entities.User;
import luke.gestionePrenotazioni.payloads.NewEdificioPayload;
import luke.gestionePrenotazioni.payloads.NewUserPayload;
import luke.gestionePrenotazioni.repository.UsersRepository;
import luke.gestionePrenotazioni.security.AuthController;
import luke.gestionePrenotazioni.service.UsersService;

@Component
public class Runner implements CommandLineRunner {
	private final UsersService srv;
	private final UsersRepository userR;
	private final EdificioController edif;
	private AuthController auth;

	@Autowired
	public Runner(UsersService srv, UsersRepository userR, EdificioController edif, AuthController auth) {
		this.srv = srv;
		this.userR = userR;
		this.edif = edif;
		this.auth = auth;
	}

	@Override
	public void run(String... args) throws Exception {

		Faker faker = new Faker(new Locale("it"));

		List<User> utentiDb = userR.findAll();
		if (utentiDb.isEmpty()) {

			for (int i = 0; i < 10; i++) {

				String name = faker.name().firstName();
				String surname = faker.name().lastName();
				String username = faker.name().username();
				String email = name.toLowerCase() + "." + surname.toLowerCase() + "@email.com";
				String password = "1234";
				NewUserPayload user = new NewUserPayload(username, name, surname, email, password);
				// auth.save(user);

			}
		}

		for (int i = 0; i < 10; i++) {
			String nomeEdificio = faker.company().name();
			String codice = faker.regexify("[A-Za-z0-9]{8}");
			NewEdificioPayload edificio = new NewEdificioPayload(nomeEdificio, codice);
			edif.save(edificio);
		}

	}

}
