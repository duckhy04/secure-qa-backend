package org.example.secureqabackend.controller;

import org.example.secureqabackend.dto.ApiResponse;
import org.example.secureqabackend.dto.CategoryDTO;
import org.example.secureqabackend.dto.CategoryQuestionsDTO;
import org.example.secureqabackend.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories();
        ApiResponse<Object> response = new ApiResponse<>("Categories get all successfully", categories);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<Object> getQuestionsByCategory(@PathVariable Long id) {
        CategoryQuestionsDTO categoryWithQuestionsDTO = categoryService.getCategoryWithQuestions(id);
        ApiResponse<Object> response = new ApiResponse<>("Get category with questions successfully", categoryWithQuestionsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO category = categoryService.saveCategory(categoryDTO);
        ApiResponse<Object> response = new ApiResponse<>("Category added successfully", category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
