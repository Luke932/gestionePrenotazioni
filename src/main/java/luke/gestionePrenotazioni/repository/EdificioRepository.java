package luke.gestionePrenotazioni.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import luke.gestionePrenotazioni.entities.Edificio;

@Repository
public interface EdificioRepository extends JpaRepository<Edificio, UUID> {

}
