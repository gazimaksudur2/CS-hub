package com.cshub.service;

import com.cshub.dto.TagDTO;
import com.cshub.entity.Tag;
import com.cshub.exception.DuplicateResourceException;
import com.cshub.exception.ResourceNotFoundException;
import com.cshub.mapper.TopicMapper;
import com.cshub.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Tag business logic.
 */
@Service
@Transactional(readOnly = true)
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    private final TagRepository tagRepository;
    private final TopicMapper topicMapper;

    public TagService(TagRepository tagRepository, TopicMapper topicMapper) {
        this.tagRepository = tagRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Get all tags.
     */
    public List<TagDTO> getAllTags() {
        logger.debug("Fetching all tags");
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(topicMapper::tagToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tag by ID.
     */
    public TagDTO getTagById(Long id) {
        logger.debug("Fetching tag with ID: {}", id);
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        return topicMapper.tagToDTO(tag);
    }

    /**
     * Create a new tag.
     */
    @Transactional
    public TagDTO createTag(String name, String color) {
        logger.debug("Creating new tag: {}", name);

        if (tagRepository.existsByName(name)) {
            throw new DuplicateResourceException("Tag", "name", name);
        }

        Tag tag = new Tag(name, color != null ? color : "#3B82F6");
        Tag savedTag = tagRepository.save(tag);
        
        logger.info("Tag created successfully with ID: {}", savedTag.getId());
        return topicMapper.tagToDTO(savedTag);
    }

    /**
     * Delete a tag.
     */
    @Transactional
    public void deleteTag(Long id) {
        logger.debug("Deleting tag with ID: {}", id);

        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag", "id", id);
        }

        tagRepository.deleteById(id);
        logger.info("Tag deleted successfully with ID: {}", id);
    }
}
