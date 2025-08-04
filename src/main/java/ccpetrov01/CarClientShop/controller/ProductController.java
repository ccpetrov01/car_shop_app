package ccpetrov01.CarClientShop.controller;

import ccpetrov01.CarClientShop.DtoViews.ProductDtoView;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.responses.ApiResponse;
import ccpetrov01.CarClientShop.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
