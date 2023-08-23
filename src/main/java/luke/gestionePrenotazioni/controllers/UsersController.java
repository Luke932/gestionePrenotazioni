package luke.gestionePrenotazioni.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import luke.gestionePrenotazioni.entities.User;
import luke.gestionePrenotazioni.payloads.NewUserPayload;
import luke.gestionePrenotazioni.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService srv;

	private UsersController(UsersService srv) {
		this.srv = srv;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User save(@RequestBody NewUserPayload body) {
		User create = srv.create(body);
		return create;
	}

	@GetMapping("")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return srv.find(page, size, sortBy);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public User findById(@PathVariable UUID id) {
		return srv.findById(id);
	}

	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public User updateUser(@PathVariable UUID id, @RequestBody NewUserPayload body) {
		return srv.findByIdAndUpdate(id, body);
	}

	@DeleteMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(UUID id) {
		srv.findByIdAndDelete(id);
	}

}
