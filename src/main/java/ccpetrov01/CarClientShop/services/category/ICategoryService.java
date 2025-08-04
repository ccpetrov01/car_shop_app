package ccpetrov01.CarClientShop.services.category;

import ccpetrov01.CarClientShop.DtoViews.CategoryViewDto;
import ccpetrov01.CarClientShop.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    void deleteCategoryById(Long id);
    Category updateCategoryById(Category category , Long id);
    Category getCategoryByName(String name);
    Category getCategoryById(Long id);

    Category addCategory(Category category);

    CategoryViewDto convertToDtoView(Category category);

    List<CategoryViewDto> convertToDtoViewList(List<Category> category);
}
