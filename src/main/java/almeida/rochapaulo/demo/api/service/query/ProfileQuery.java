package almeida.rochapaulo.demo.api.service.query;

import java.util.concurrent.CompletableFuture;

import almeida.rochapaulo.demo.entities.UserProfile;

/**
 * 
 * @author rochapaulo
 *
 */
public interface ProfileQuery {

	CompletableFuture<UserProfile> execute();
	
}
