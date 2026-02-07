package com.cshub.mapper;

import com.cshub.dto.TagDTO;
import com.cshub.dto.TopicDTO;
import com.cshub.entity.Tag;
import com.cshub.entity.Topic;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper to convert between Entity and DTO.
 * Demonstrates OOP principle: Single Responsibility - separate conversion logic.
 */
@Component
public class TopicMapper {

    /**
     * Convert Topic entity to TopicDTO.
     * Does not include children to avoid infinite recursion.
     */
    public TopicDTO toDTO(Topic topic) {
        if (topic == null) {
            return null;
        }

        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setSlug(topic.getSlug());
        dto.setContent(topic.getContent());
        dto.setParentId(topic.getParent() != null ? topic.getParent().getId() : null);
        dto.setDisplayOrder(topic.getDisplayOrder());
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setUpdatedAt(topic.getUpdatedAt());

        // Map tags
        dto.setTags(topic.getTags().stream()
                .map(this::tagToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    /**
     * Convert Topic entity to TopicDTO with children (for tree structure).
     * Recursively converts children.
     */
    public TopicDTO toDTOWithChildren(Topic topic) {
        TopicDTO dto = toDTO(topic);
        if (dto != null && topic.hasChildren()) {
            dto.setChildren(topic.getChildren().stream()
                    .map(this::toDTOWithChildren) // Recursive call
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    /**
     * Convert Tag entity to TagDTO.
     */
    public TagDTO tagToDTO(Tag tag) {
        if (tag == null) {
            return null;
        }
        return new TagDTO(tag.getId(), tag.getName(), tag.getColor());
    }

    /**
     * Convert TopicDTO to Topic entity (for creation).
     */
    public Topic toEntity(TopicDTO dto) {
        if (dto == null) {
            return null;
        }

        Topic topic = new Topic();
        topic.setTitle(dto.getTitle());
        topic.setSlug(dto.getSlug());
        topic.setContent(dto.getContent());
        topic.setDisplayOrder(dto.getDisplayOrder());

        return topic;
    }
}
