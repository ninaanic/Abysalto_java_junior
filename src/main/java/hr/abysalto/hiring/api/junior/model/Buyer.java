package hr.abysalto.hiring.api.junior.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "buyer")
public class Buyer {
	@Id
	@Column("buyer_id")
	private Long buyerId;
	@Column("first_name")
	private String firstName;
	@Column("last_name")
	private String lastName;
	@Column("title")
	private String title;
}
