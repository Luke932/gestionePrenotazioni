package luke.gestionePrenotazioni.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "edifici")
@Data
@NoArgsConstructor
public class Edificio {

	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String codice;

	public Edificio(String name, String codice) {
		this.name = name;
		this.codice = codice;
	}

}
