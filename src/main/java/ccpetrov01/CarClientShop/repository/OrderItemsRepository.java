package ccpetrov01.CarClientShop.repository;

import ccpetrov01.CarClientShop.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems , Long> {
}
