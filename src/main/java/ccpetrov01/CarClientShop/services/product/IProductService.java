package ccpetrov01.CarClientShop.services.product;

import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.requests.AddProductRequest;
import ccpetrov01.CarClientShop.requests.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest request, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    List<Product> getByCategoryName(String Category);
    List<Product> getByBrand(String brand);

}
