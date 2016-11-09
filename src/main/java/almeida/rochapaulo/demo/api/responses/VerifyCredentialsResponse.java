package almeida.rochapaulo.demo.api.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author rochapaulo
 *
 */
@Data
@AllArgsConstructor
public class VerifyCredentialsResponse {

    private final UUID userId;
}
