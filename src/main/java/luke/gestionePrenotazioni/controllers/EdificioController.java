package luke.gestionePrenotazioni.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import luke.gestionePrenotazioni.entities.Edificio;
import luke.gestionePrenotazioni.payloads.NewEdificioPayload;
import luke.gestionePrenotazioni.service.EdificioService;

@RestController
@RequestMapping("/edifici")
public class EdificioController {
	private final EdificioService srv;
	private final PasswordEncoder bcrypt;

	public EdificioController(EdificioService srv, PasswordEncoder bcrypt) {
		this.srv = srv;
		this.bcrypt = bcrypt;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Edificio save(@RequestBody NewEdificioPayload body) {
		body.setCodice(bcrypt.encode(body.getCodice()));
		Edificio create = srv.save(body);
		return create;
	}

	@GetMapping("")
	public Page<Edificio> getEdifici(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return srv.find(page, size, sortBy);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Edificio findById(@PathVariable UUID id) {
		return srv.findById(id);
	}

	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Edificio findByIdAndUpdate(@PathVariable UUID id, @RequestBody NewEdificioPayload body) {
		return srv.findByIdAndUpdate(id, body);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// @PreAuthorize("hasAuthority('ADMIN')")
	public void delete(@PathVariable UUID id) {
		srv.findByIdAndDelete(id);
	}

}
