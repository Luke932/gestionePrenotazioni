package luke.gestionePrenotazioni.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(UUID id) {
		super("Siamo spiacenti l'ID: " + id + " non è stato trovato");
	}
}
