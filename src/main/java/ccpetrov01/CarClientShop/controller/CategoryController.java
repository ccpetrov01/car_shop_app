package ccpetrov01.CarClientShop.controller;

import ccpetrov01.CarClientShop.DtoViews.CategoryViewDto;
import ccpetrov01.CarClientShop.DtoViews.ProductDtoView;
import ccpetrov01.CarClientShop.exceptions.AlreadyExistsException;
import ccpetrov01.CarClientShop.exceptions.ResourceNotFoundException;
import ccpetrov01.CarClientShop.models.Category;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.requests.UpdateProductRequest;
import ccpetrov01.CarClientShop.responses.ApiResponse;
import ccpetrov01.CarClientShop.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        List<Category> category = categoryService.getAllCategories();
        List<CategoryViewDto> categoryView = categoryService.convertToDtoViewList(category);
        return ResponseEntity.ok(new ApiResponse("Successfully getting all categories!" , categoryView));
    }

    @DeleteMapping("/category/{category_id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long category_id){
        try{
            categoryService.deleteCategoryById(category_id);
            return ResponseEntity.ok(new ApiResponse("Successfully deleting the category!", category_id));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{category_id/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody Category request, @PathVariable Long category_id){
        try{
            Category category = categoryService.updateCategoryById(request, category_id);
            CategoryViewDto categoryViewDto = categoryService.convertToDtoView(category);
            return ResponseEntity.ok(new ApiResponse("Successfully updating the category!", categoryViewDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/{name}/get-by-name")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category category = categoryService.getCategoryByName(name);
            CategoryViewDto categoryViewDto = categoryService.convertToDtoView(category);
            return ResponseEntity.ok(new ApiResponse("Successfully getting the category!", categoryViewDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Category with this name doesn't exists!",null));
        }
    }

    @GetMapping("/{category_id}/get-by-categoryId")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable Long category_id){
        try{
            Category category = categoryService.getCategoryById(category_id);
            CategoryViewDto categoryViewDto = categoryService.convertToDtoView(category);
            return ResponseEntity.ok(new ApiResponse("Successfully getting the category!", categoryViewDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Category with this ID doesn't exists!",null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try{
            categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Successfully adding new the category!", null));
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }



}

