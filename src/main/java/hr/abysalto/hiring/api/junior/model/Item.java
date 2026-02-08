package hr.abysalto.hiring.api.junior.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "item")
public class Item {
    @Id
    @Column("item_id")
    private Long itemId;

    @Column("name")
    private String name;

    @Column("price")
    private BigDecimal price;
}
