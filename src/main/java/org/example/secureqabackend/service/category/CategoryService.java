package org.example.secureqabackend.service.category;

import org.example.secureqabackend.dto.CategoryDTO;
import org.example.secureqabackend.dto.CategoryQuestionsDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAllCategories();

    CategoryQuestionsDTO getCategoryWithQuestions(Long id);

    CategoryDTO findCategoryById(Long id);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
