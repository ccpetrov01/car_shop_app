package ccpetrov01.CarClientShop.repository;

import ccpetrov01.CarClientShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
