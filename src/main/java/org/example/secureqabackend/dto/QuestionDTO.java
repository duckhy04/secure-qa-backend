package org.example.secureqabackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.secureqabackend.enums.QuestionDifficulty;
import org.example.secureqabackend.enums.QuestionStatus;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionDTO extends TimeDTO {
    private Long id;
    private String title;
    private String content;
    private QuestionDifficulty difficulty;
    private QuestionStatus status;
    private int viewsCount;
    private UserDTO user;
    private Set<TagDTO> tags;
    private Set<CategoryDTO> categories;
}
