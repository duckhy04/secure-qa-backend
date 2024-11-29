package org.example.secureqabackend.controller;

import org.example.secureqabackend.dto.ApiResponse;
import org.example.secureqabackend.dto.TagDTO;
import org.example.secureqabackend.service.tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllTags() {
        List<TagDTO> tags = tagService.getAllTags();
        ApiResponse<Object> response = new ApiResponse<>("Get all tags successfully", tags);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable Long id) {
        TagDTO tag = tagService.getTagById(id);
        ApiResponse<Object> response = new ApiResponse<>("Get tag successfully", tag);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
