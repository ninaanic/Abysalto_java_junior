package hr.abysalto.hiring.api.junior.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AccessType(AccessType.Type.PROPERTY)
@Table(name = "orders")
public class Order {
	@Id
	@Column("order_nr")
	private Long orderNr;

	@Column("buyer_id")
	private Long buyerId;

	// private Buyer buyer;

	@Column("order_status")
	private OrderStatus orderStatus;

	@Column("order_time")
	private LocalDateTime orderTime;

	// private List<OrderItem> orderItems;

	@Column("payment_option")
	private PaymentOption paymentOption;

	@Column("delivery_address_id")
	private Long deliveryAddressId;

	// private BuyerAddress deliveryAddress;

	@Column("contact_number")
	private String contactNumber;

	@Column("currency")
	private String currency;

	@Column("total_price")
	private BigDecimal totalPrice;
}
