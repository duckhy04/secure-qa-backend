package org.example.secureqabackend.service.question;

import org.example.secureqabackend.dto.CategoryQuestionsDTO;
import org.example.secureqabackend.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> getAllQuestions();

    QuestionDTO getQuestionById(Long id);

    QuestionDTO saveQuestion(QuestionDTO questionDTO);

    QuestionDTO updateQuestion(QuestionDTO questionDTO);

    void deleteQuestionById(long id);
}
