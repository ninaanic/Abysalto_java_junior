package hr.abysalto.hiring.api.junior.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	@Column("order_id")
	private Long orderId;

	@Column("buyer_id")
	private Long buyerId;

	@Transient
	private Buyer buyer;

	@Column("order_status")
	private OrderStatus orderStatus;

	@Column("order_time")
	private LocalDateTime orderTime;

	@Transient
	private List<OrderItem> orderItems = new ArrayList<>();;

	@Column("payment_option")
	private PaymentOption paymentOption;

	@Column("delivery_address_id")
	private Long deliveryAddressId;

	@Transient
	private BuyerAddress deliveryAddress;

	@Column("contact_number")
	private String contactNumber;

	@Column("currency")
	private String currency;

	@Column("total_price")
	private BigDecimal totalPrice;
}
