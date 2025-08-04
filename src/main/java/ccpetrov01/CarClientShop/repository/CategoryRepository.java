package ccpetrov01.CarClientShop.repository;

import ccpetrov01.CarClientShop.DtoViews.CategoryViewDto;
import ccpetrov01.CarClientShop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
