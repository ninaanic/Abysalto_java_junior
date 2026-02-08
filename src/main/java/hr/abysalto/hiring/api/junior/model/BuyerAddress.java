package hr.abysalto.hiring.api.junior.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "buyer_address")
public class BuyerAddress {
	@Id
	@Column("buyer_address_id")
	private Long buyerAddressId;
	@Column("city")
	private String city;
	@Column("street")
	private String street;
	@Column("home_number")
	private String homeNumber;
}
