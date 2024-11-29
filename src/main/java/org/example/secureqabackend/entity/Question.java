package org.example.secureqabackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.secureqabackend.dto.CategoryDTO;
import org.example.secureqabackend.dto.QuestionDTO;
import org.example.secureqabackend.dto.TagDTO;
import org.example.secureqabackend.dto.UserDTO;
import org.example.secureqabackend.enums.QuestionDifficulty;
import org.example.secureqabackend.enums.QuestionStatus;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "questions")
public class Question extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @Enumerated(EnumType.STRING)
    private QuestionDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

    @Column(name = "views_count", nullable = false)
    private int viewsCount = 0;

    public QuestionDTO questionDTO() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        questionDTO.setTitle(title);
        questionDTO.setContent(content);
        questionDTO.setDifficulty(difficulty);
        questionDTO.setStatus(status);
        questionDTO.setViewsCount(viewsCount);

        Optional.ofNullable(user).ifPresent(u -> {
            UserDTO userDTO = u.userDTO();
            questionDTO.setUser(userDTO);
        });

        Optional.ofNullable(categories)
                .filter(c -> !c.isEmpty())
                .ifPresent(c -> {
                    Set<CategoryDTO> categoryDTOs = c.stream()
                            .map(Category::categoryDTO)
                            .collect(Collectors.toSet());
                    questionDTO.setCategories(categoryDTOs);
                });

        Optional.ofNullable(tags)
                .filter(t -> !t.isEmpty())
                .ifPresent(t -> {
                    Set<TagDTO> tagDTOs = t.stream()
                            .map(Tag::tagDTO)
                            .collect(Collectors.toSet());
                    questionDTO.setTags(tagDTOs);
                });

        questionDTO.setCreatedAt(String.valueOf(getCreatedAt()));
        questionDTO.setUpdatedAt(String.valueOf(getUpdatedAt()));

        return questionDTO;
    }

}
