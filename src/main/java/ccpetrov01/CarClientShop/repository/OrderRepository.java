package ccpetrov01.CarClientShop.repository;

import ccpetrov01.CarClientShop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {
}
