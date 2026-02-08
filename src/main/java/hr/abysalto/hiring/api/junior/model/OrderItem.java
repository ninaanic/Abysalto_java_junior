package hr.abysalto.hiring.api.junior.model;

import java.math.BigDecimal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "order_item")
public class OrderItem {
	@Id
	@Column("order_item_id")
	private Long orderItemId;

	@Column("order_id")
	private Long orderId;

	@Column("item_id")
	private Long itemId;

	@Column("name")
	private String name;

	@Column("quantity")
	private Short quantity;

	@Column("price")
	private BigDecimal price;
}
