package org.example.secureqabackend.controller;

import org.example.secureqabackend.dto.ApiResponse;
import org.example.secureqabackend.dto.QuestionDTO;
import org.example.secureqabackend.service.question.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        ApiResponse<Object> response = new ApiResponse<>("Get all questions successfully", questions);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuestionById(@PathVariable Long id) {
        QuestionDTO question = questionService.getQuestionById(id);
        ApiResponse<Object> response = new ApiResponse<>("Get question successfully", question);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> saveQuestion(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO question = questionService.saveQuestion(questionDTO);
        ApiResponse<Object> response = new ApiResponse<>("Created question successfully", question);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
