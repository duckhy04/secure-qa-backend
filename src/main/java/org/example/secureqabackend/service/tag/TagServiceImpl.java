package org.example.secureqabackend.service.tag;

import jakarta.annotation.PostConstruct;
import org.example.secureqabackend.dto.TagDTO;
import org.example.secureqabackend.entity.Tag;
import org.example.secureqabackend.exception.ResourceNotFoundException;
import org.example.secureqabackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PostConstruct
    public void initTags() {
        List<String> initialTags = Arrays.asList("security", "web", "database", "network", "java");

        for (String tagName : initialTags) {
            if (!tagRepository.existsByName(tagName)) {
                Tag tag = new Tag();
                tag.setName(tagName);
                tagRepository.save(tag);
            }
        }
    }

    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(Tag::tagDTO).collect(Collectors.toList());
    }

    @Override
    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tag with id " + id + " not found")
        );
        return tag.tagDTO();

    }
}
