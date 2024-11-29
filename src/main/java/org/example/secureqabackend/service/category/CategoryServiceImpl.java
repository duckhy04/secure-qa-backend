package org.example.secureqabackend.service.category;

import jakarta.annotation.PostConstruct;
import org.example.secureqabackend.dto.CategoryDTO;
import org.example.secureqabackend.dto.CategoryQuestionsDTO;
import org.example.secureqabackend.dto.QuestionDTO;
import org.example.secureqabackend.entity.Category;
import org.example.secureqabackend.entity.Question;
import org.example.secureqabackend.exception.AlreadyExistsException;
import org.example.secureqabackend.exception.CustomIllegalArgumentException;
import org.example.secureqabackend.exception.ResourceNotFoundException;
import org.example.secureqabackend.repository.CategoryRepository;
import org.example.secureqabackend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(Category::categoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
        return null;
    }

    @Override
    public CategoryQuestionsDTO getCategoryWithQuestions(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );

        List<Question> questions = questionRepository.findByCategoriesId(id);

        CategoryQuestionsDTO categoryWithQuestionsDTO = new CategoryQuestionsDTO();
        categoryWithQuestionsDTO.setCategory(category.categoryDTO());

        List<QuestionDTO> questionDTOs = questions.stream()
                .map(Question::questionDTO)
                .collect(Collectors.toList());
        categoryWithQuestionsDTO.setQuestion(questionDTOs);

        return categoryWithQuestionsDTO;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category existCategory = categoryRepository.findByName(categoryDTO.getName());

        if (existCategory != null) {
            throw new AlreadyExistsException("Category with name " + categoryDTO.getName() + " already exists");
        }

        Category category = new Category();

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        } else {
            throw new CustomIllegalArgumentException("Category name cannot be empty");
        }
        category.setDescription(categoryDTO.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.categoryDTO();
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @PostConstruct
    public void initCategories() {
        List<String> initialCategories = Arrays.asList(
                "Web Security",
                "Database Security",
                "Network Security",
                "Cloud Security",
                "Application Security"
        );

        for (String categoryName : initialCategories) {
            if (!categoryRepository.existsByName(categoryName)) {
                Category category = new Category();
                category.setName(categoryName);
                category.setDescription(categoryName + " related topics");
                categoryRepository.save(category);
            }
        }
    }
}
