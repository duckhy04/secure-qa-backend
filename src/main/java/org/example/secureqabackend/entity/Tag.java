package org.example.secureqabackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.secureqabackend.dto.TagDTO;

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
