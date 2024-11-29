package org.example.secureqabackend.service.tag;

import org.example.secureqabackend.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTags();
    TagDTO getTagById(Long id);
}
