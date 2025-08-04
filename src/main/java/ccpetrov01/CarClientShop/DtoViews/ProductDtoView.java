package ccpetrov01.CarClientShop.DtoViews;

import ccpetrov01.CarClientShop.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDtoView {

    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private CategoryViewDto category;
}
