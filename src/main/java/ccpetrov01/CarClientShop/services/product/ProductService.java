package ccpetrov01.CarClientShop.services.product;

import ccpetrov01.CarClientShop.exceptions.AlreadyExistsException;
import ccpetrov01.CarClientShop.exceptions.ResourceNotFoundException;
import ccpetrov01.CarClientShop.models.Category;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.repository.CategoryRepository;
import ccpetrov01.CarClientShop.repository.ProductImageRepository;
import ccpetrov01.CarClientShop.repository.ProductRepository;
import ccpetrov01.CarClientShop.requests.AddProductRequest;
import ccpetrov01.CarClientShop.requests.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product addProduct(AddProductRequest request) {
        if(productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistsException(
                    request.getName() + " " + request.getBrand() + " Already Exists, You May Update This Product Instead.");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Boolean productExists(String name, String brand) {
        return productRepository.existsByBrandAndName(brand, name);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with this Id doesn't exists!"));
    }

    private Product createProduct(AddProductRequest request , Category category){
        return new Product(request.getName() , request.getBrand(), request.getPrice() , request.getInventory()
        , request.getDescription() , category);
    }

    @Override
    public void deleteProductById(Long id) {
         productRepository.findById(id).ifPresentOrElse(
                 productRepository::delete
         , ()-> {
                     throw new ResourceNotFoundException("Product with this id doesn't exists and cannot be deleted!");
                 });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists and cannot be updated"));
    }

    private Product updateExistingProduct(Product existingProduct , UpdateProductRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;


    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getByCategoryName(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }
}
