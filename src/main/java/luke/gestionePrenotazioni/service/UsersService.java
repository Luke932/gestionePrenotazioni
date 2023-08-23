package luke.gestionePrenotazioni.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import luke.gestionePrenotazioni.entities.User;
import luke.gestionePrenotazioni.exceptions.BadRequestException;
import luke.gestionePrenotazioni.exceptions.NotFoundException;
import luke.gestionePrenotazioni.payloads.NewUserPayload;
import luke.gestionePrenotazioni.repository.UsersRepository;

@Service
public class UsersService {
	private final UsersRepository userP;

	@Autowired
	public UsersService(UsersRepository userP) {
		this.userP = userP;
	}

	public User create(NewUserPayload body) {
		userP.findByEmail(body.getEmail()).ifPresent(user -> {
			throw new BadRequestException("L'email è stata già utilizzata");
		});
		User newUser = new User(body.getUsername(), body.getName(), body.getSurname(), body.getEmail(),
				body.getPassword());
		return userP.save(newUser);
	}

	public List<User> getUsers() {
		return userP.findAll();
	}

	public Page<User> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return userP.findAll(pageable);
	}

	public User findById(UUID id) throws NotFoundException {
		return userP.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public User findByIdAndUpdate(UUID id, NewUserPayload body) throws NotFoundException {
		User found = this.findById(id);
		found.setUsername(body.getUsername());
		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setEmail(body.getEmail());
		return userP.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		userP.delete(found);
	}

	public User findByEmail(String email) {
		return userP.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email" + email + "non trovato"));
	}
}
