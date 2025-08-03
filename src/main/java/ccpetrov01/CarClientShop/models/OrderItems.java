package ccpetrov01.CarClientShop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItems {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orderItems_generator"
    )
    @SequenceGenerator(
            name="orderItems_generator",
            sequenceName = "orderItems_generator",
            allocationSize = 1
    )
    private Long id;
    private int quantity;
    private BigDecimal price;

    public OrderItems(int quantity, BigDecimal price) {
        this.quantity = quantity;
        this.price = price;
    }
}
