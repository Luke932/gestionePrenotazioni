package luke.gestionePrenotazioni.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import luke.gestionePrenotazioni.entities.Edificio;
import luke.gestionePrenotazioni.exceptions.NotFoundException;
import luke.gestionePrenotazioni.payloads.NewEdificioPayload;
import luke.gestionePrenotazioni.repository.EdificioRepository;

@Service
public class EdificioService {
	private final EdificioRepository edif;

	@Autowired
	public EdificioService(EdificioRepository edif) {
		this.edif = edif;
	}

	public Edificio save(NewEdificioPayload body) {
		Edificio newEdif = new Edificio(body.getName(), body.getCodice());
		return edif.save(newEdif);
	}

	public List<Edificio> getEdifici() {
		return edif.findAll();
	}

	public Page<Edificio> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return edif.findAll(pageable);
	}

	public Edificio findById(UUID id) throws NotFoundException {
		return edif.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public Edificio findByIdAndUpdate(UUID id, NewEdificioPayload body) {
		Edificio found = this.findById(id);
		found.setName(body.getName());
		return edif.save(found);
	}

	public void findByIdAndDelete(UUID id) {
		Edificio found = this.findById(id);
		edif.delete(found);
	}
}
