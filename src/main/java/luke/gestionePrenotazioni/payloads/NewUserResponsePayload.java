package luke.gestionePrenotazioni.payloads;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class NewUserResponsePayload {
	private UUID id;
}
