package org.example.secureqabackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryQuestionsDTO {
    private CategoryDTO category;
    private List<QuestionDTO> question;
}
