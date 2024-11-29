package org.example.secureqabackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.secureqabackend.dto.CategoryDTO;
import org.example.secureqabackend.dto.QuestionDTO;
import org.example.secureqabackend.dto.TagDTO;
import org.example.secureqabackend.dto.UserDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public TagDTO tagDTO() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(id);
        tagDTO.setName(name);
        return tagDTO;
    }
}
