package org.example.secureqabackend.service.question;

import org.example.secureqabackend.dto.CategoryDTO;
import org.example.secureqabackend.dto.CategoryQuestionsDTO;
import org.example.secureqabackend.dto.QuestionDTO;
import org.example.secureqabackend.dto.TagDTO;
import org.example.secureqabackend.entity.Category;
import org.example.secureqabackend.entity.Question;
import org.example.secureqabackend.entity.Tag;
import org.example.secureqabackend.entity.User;
import org.example.secureqabackend.enums.QuestionStatus;
import org.example.secureqabackend.exception.CustomIllegalArgumentException;
import org.example.secureqabackend.exception.ResourceNotFoundException;
import org.example.secureqabackend.repository.CategoryRepository;
import org.example.secureqabackend.repository.QuestionRepository;
import org.example.secureqabackend.repository.TagRepository;
import org.example.secureqabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, UserRepository userRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(Question::questionDTO).collect(Collectors.toList());
    }

    @Override
    public QuestionDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Question not found")
        );
        return question.questionDTO();
    }

    @Override
    public QuestionDTO saveQuestion(QuestionDTO questionDTO) {
        Question question = new Question();

        if (questionDTO.getUser() != null) {
            User user = userRepository.findById(questionDTO.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            question.setUser(user);
        } else {
            throw new ResourceNotFoundException("User must not be null");
        }

        if (questionDTO.getCategories() != null && !questionDTO.getCategories().isEmpty()) {
            Set<Category> categories = new HashSet<>();
            for (CategoryDTO categoryDTO : questionDTO.getCategories()) {
                Category category = categoryRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                categories.add(category);
            }
            question.setCategories(categories);
        } else {
            throw new ResourceNotFoundException("Categories must not be null or empty");
        }

        if (questionDTO.getTags() != null && !questionDTO.getTags().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (TagDTO tagDTO : questionDTO.getTags()) {
                Tag tag = tagRepository.findById(tagDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
                tags.add(tag);
            }
            question.setTags(tags);
        } else {
            throw new ResourceNotFoundException("Tags must not be null or empty");
        }

        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setDifficulty(questionDTO.getDifficulty());
        question.setStatus(QuestionStatus.NEW);

        Question savedQuestion = questionRepository.save(question);

        return savedQuestion.questionDTO();
    }

    @Override
    public QuestionDTO updateQuestion(QuestionDTO questionDTO) {
        return null;
    }

    @Override
    public void deleteQuestionById(long id) {

    }
}
