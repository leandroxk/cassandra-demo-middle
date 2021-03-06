package almeida.rochalabs.demo.data.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import almeida.rochalabs.demo.data.entities.UserProfile;

/**
 * 
 * @author rochapaulo
 *
 */
public class ProfileByUserID implements ProfileQuery {

    private final Mapper<UserProfile> profileMapper;
    private final UUID uuid;

    public ProfileByUserID(MappingManager manager, UUID uuid) {
        
        profileMapper = manager.mapper(UserProfile.class);
        this.uuid = uuid;
    }

    @Override
    public ListenableFuture<List<UserProfile>> execute() {

        return Futures.transform(profileMapper.getAsync(uuid), wrap2List());
    }

    private Function<UserProfile, List<UserProfile>> wrap2List() {
        
        return (Function<UserProfile, List<UserProfile>>) 
        		profile -> profile != null ? Arrays.asList(profile) : Collections.emptyList();
    }
 
}
