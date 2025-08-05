package ccpetrov01.CarClientShop.controller;

import ccpetrov01.CarClientShop.DtoViews.ProductDtoView;
import ccpetrov01.CarClientShop.exceptions.AlreadyExistsException;
import ccpetrov01.CarClientShop.exceptions.ResourceNotFoundException;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.requests.AddProductRequest;
import ccpetrov01.CarClientShop.requests.UpdateProductRequest;
import ccpetrov01.CarClientShop.responses.ApiResponse;
import ccpetrov01.CarClientShop.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDtoView> productDtoViews = productService.convertToDtoViewList(products);
        return ResponseEntity.ok(new ApiResponse("Successfully getting all products!" , productDtoViews));
    }
    @GetMapping("/product/{product_id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long product_id){
        try {
            Product product = productService.getProductById(product_id);
            ProductDtoView productDtoView = productService.convertToDtoView(product);
            return ResponseEntity.ok(new ApiResponse("Successfully getting product!" , productDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/product/{product_id}/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long product_id){
        try{
            productService.deleteProductById(product_id);
            return ResponseEntity.ok(new ApiResponse("Successfully deleting the product!", product_id));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/product/{product_id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long product_id){
        try{
            Product product = productService.updateProduct(request, product_id);
            ProductDtoView productDtoView = productService.convertToDtoView(product);
            return ResponseEntity.ok(new ApiResponse("Successfully updating the product!", productDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/product/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){
        try{
            Product product = productService.addProduct(request);
            ProductDtoView productDtoView = productService.convertToDtoView(product);
            return ResponseEntity.ok(new ApiResponse("Successfully adding the product!", productDtoView));
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/product/{name}/get-by-name")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try{
            List<Product> product = productService.getProductsByName(name);
            if (product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            List<ProductDtoView> productDtoView = productService.convertToDtoViewList(product);
            return ResponseEntity.ok(new ApiResponse("Successfully getting the product!", productDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product with this name doesn't exists!",null));
        }
    }

    @GetMapping("/product/get-by-name-and-brand")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String name , @RequestParam String brand){
        try{
            List<Product> product = productService.getProductsByBrandAndName(brand, name);
            if (product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            List<ProductDtoView> productDtoView = productService.convertToDtoViewList(product);
            return ResponseEntity.ok(new ApiResponse("Successfully getting the product!", productDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product with this name and brand doesn't exists!",null));
        }
    }

    @GetMapping("/product/{category}/get-by-category")
    public ResponseEntity<ApiResponse> getByCategoryName(@PathVariable String category){
        try{
            List<Product> product = productService.getByCategoryName(category);
            if (product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No category found ", null));
            }
            List<ProductDtoView> productDtoView = productService.convertToDtoViewList(product);
            return ResponseEntity.ok(new ApiResponse("Successfully getting the category!", productDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product with this category doesn't exists!",null));
        }
    }

    @GetMapping("/product/{brand}/get-by-brand")
    public ResponseEntity<ApiResponse> getByBrand(@PathVariable String brand){
        try{
            List<Product> product = productService.getByBrand(brand);
            if (product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No brand found ", null));
            }
            List<ProductDtoView> productDtoView = productService.convertToDtoViewList(product);
            return ResponseEntity.ok(new ApiResponse("Successfully getting the brand!", productDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product with this brand doesn't exists!",null));
        }
    }



}
