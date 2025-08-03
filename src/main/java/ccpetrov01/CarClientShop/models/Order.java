package ccpetrov01.CarClientShop.models;

import ccpetrov01.CarClientShop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_generator"
    )
    @SequenceGenerator(
            name="orders_generator",
            sequenceName = "orders_generator",
            allocationSize = 1
    )
    private Long id;
    private BigDecimal price;
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    public Order(BigDecimal price, LocalDate orderDate) {
        this.price = price;
        this.orderDate = orderDate;
    }
}
