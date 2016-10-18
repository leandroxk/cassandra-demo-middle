package almeida.rochapaulo.demo.entities;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Table(name = "users")
public class UserProfile {

	@PartitionKey
	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "since")
	private Date since;
	
}