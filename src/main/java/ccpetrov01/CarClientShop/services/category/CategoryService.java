package ccpetrov01.CarClientShop.services.category;

import ccpetrov01.CarClientShop.exceptions.AlreadyExistsException;
import ccpetrov01.CarClientShop.exceptions.ResourceNotFoundException;
import ccpetrov01.CarClientShop.models.Category;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.repository.CategoryRepository;
import ccpetrov01.CarClientShop.requests.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                categoryRepository::delete
                , () -> {
                    throw new ResourceNotFoundException("Category with this id doesn't exists and cannot be deleted!");
                });

    }
    @Override
    public Category updateCategoryById(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category with this id doesn't exist and cannot be updated!"));
    }


    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with this Id doesn't exists!"));
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(existingCategory -> !categoryRepository.existByName(existingCategory.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException("Category with this name already exists!"));
    }
}
