package almeida.rochapaulo.demo.entities;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Data
@Table(name = "photo_rank")
public class PhotoRank {

	@Column(name = "photo_id")
	private UUID photoId;

	@PartitionKey
	@Column(name = "stars")
	private int stars = 0;

	@ClusteringColumn
	@Column(name = "votes")
	private long votes = 0;

}