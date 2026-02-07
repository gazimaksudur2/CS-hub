package com.cshub.service;

import com.cshub.dto.CreateTopicRequest;
import com.cshub.dto.TopicDTO;
import com.cshub.entity.Tag;
import com.cshub.entity.Topic;
import com.cshub.exception.DuplicateResourceException;
import com.cshub.exception.ResourceNotFoundException;
import com.cshub.mapper.TopicMapper;
import com.cshub.repository.TagRepository;
import com.cshub.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service layer for Topic business logic.
 * Demonstrates DSA: Recursive tree building and HashMap optimization to avoid N+1 problem.
 */
@Service
@Transactional(readOnly = true)
public class TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TagRepository tagRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.tagRepository = tagRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Get all topics organized in a tree hierarchy.
     * 
     * THE KILLER FEATURE for interviews!
     * This method demonstrates:
     * 1. DSA: Tree data structure
     * 2. Algorithm: O(n) complexity using HashMap
     * 3. DBMS: Solving N+1 problem with single query
     * 
     * Interview Answer:
     * "I fetch all topics in a single query to avoid the N+1 select problem,
     * then reconstruct the tree in memory using a HashMap (O(n) time complexity)."
     */
    public List<TopicDTO> getTopicTree() {
        logger.debug("Building topic tree");

        // Fetch all topics with their tags in ONE query (solves N+1 problem)
        List<Topic> allTopics = topicRepository.findAllWithTags();
        
        // Build HashMap for O(1) lookup - Key: topic ID, Value: TopicDTO
        Map<Long, TopicDTO> topicMap = new HashMap<>();
        List<TopicDTO> rootTopics = new ArrayList<>();

        // First pass: Convert all topics to DTOs and put in map
        for (Topic topic : allTopics) {
            TopicDTO dto = topicMapper.toDTO(topic);
            topicMap.put(topic.getId(), dto);
        }

        // Second pass: Build the tree structure
        for (Topic topic : allTopics) {
            TopicDTO dto = topicMap.get(topic.getId());
            
            if (topic.getParent() == null) {
                // Root topic - add to result list
                rootTopics.add(dto);
            } else {
                // Child topic - add to parent's children list
                TopicDTO parentDTO = topicMap.get(topic.getParent().getId());
                if (parentDTO != null) {
                    parentDTO.getChildren().add(dto);
                }
            }
        }

        // Sort by display order
        rootTopics.sort(Comparator.comparing(TopicDTO::getDisplayOrder));
        rootTopics.forEach(this::sortChildren);

        logger.debug("Topic tree built with {} root topics", rootTopics.size());
        return rootTopics;
    }

    /**
     * Recursively sort children by display order.
     * Demonstrates DSA: Recursion.
     */
    private void sortChildren(TopicDTO topic) {
        if (topic.getChildren() != null && !topic.getChildren().isEmpty()) {
            topic.getChildren().sort(Comparator.comparing(TopicDTO::getDisplayOrder));
            topic.getChildren().forEach(this::sortChildren); // Recursive call
        }
    }

    /**
     * Get a single topic by slug.
     */
    public TopicDTO getTopicBySlug(String slug) {
        logger.debug("Fetching topic with slug: {}", slug);
        Topic topic = topicRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "slug", slug));
        return topicMapper.toDTO(topic);
    }

    /**
     * Get a single topic by ID.
     */
    public TopicDTO getTopicById(Long id) {
        logger.debug("Fetching topic with ID: {}", id);
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));
        return topicMapper.toDTO(topic);
    }

    /**
     * Search topics by keyword.
     * Demonstrates Algorithm: Search implementation.
     */
    public List<TopicDTO> searchTopics(String keyword) {
        logger.debug("Searching topics with keyword: {}", keyword);
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<Topic> topics = topicRepository.searchByKeyword(keyword.trim());
        return topics.stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get topics by tag name.
     */
    public List<TopicDTO> getTopicsByTag(String tagName) {
        logger.debug("Fetching topics with tag: {}", tagName);
        List<Topic> topics = topicRepository.findByTagName(tagName);
        return topics.stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new topic.
     */
    @Transactional
    public TopicDTO createTopic(CreateTopicRequest request) {
        logger.debug("Creating new topic: {}", request.getTitle());

        // Validate slug uniqueness
        if (topicRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Topic", "slug", request.getSlug());
        }

        Topic topic = new Topic();
        topic.setTitle(request.getTitle());
        topic.setSlug(request.getSlug());
        topic.setContent(request.getContent());
        topic.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        // Set parent if provided
        if (request.getParentId() != null) {
            Topic parent = topicRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Topic", "id", request.getParentId()));
            topic.setParent(parent);
        }

        // Add tags if provided
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
                tags.add(tag);
            }
            topic.setTags(tags);
        }

        Topic savedTopic = topicRepository.save(topic);
        logger.info("Topic created successfully with ID: {}", savedTopic.getId());
        
        return topicMapper.toDTO(savedTopic);
    }

    /**
     * Update an existing topic.
     */
    @Transactional
    public TopicDTO updateTopic(Long id, CreateTopicRequest request) {
        logger.debug("Updating topic with ID: {}", id);

        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));

        // Check slug uniqueness if changed
        if (!topic.getSlug().equals(request.getSlug()) && topicRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Topic", "slug", request.getSlug());
        }

        topic.setTitle(request.getTitle());
        topic.setSlug(request.getSlug());
        topic.setContent(request.getContent());
        topic.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        // Update parent if changed
        if (request.getParentId() != null) {
            Topic parent = topicRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Topic", "id", request.getParentId()));
            topic.setParent(parent);
        } else {
            topic.setParent(null);
        }

        // Update tags
        topic.getTags().clear();
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
                topic.getTags().add(tag);
            }
        }

        Topic updatedTopic = topicRepository.save(topic);
        logger.info("Topic updated successfully with ID: {}", updatedTopic.getId());
        
        return topicMapper.toDTO(updatedTopic);
    }

    /**
     * Delete a topic.
     * Cascading delete will remove all children.
     */
    @Transactional
    public void deleteTopic(Long id) {
        logger.debug("Deleting topic with ID: {}", id);

        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic", "id", id);
        }

        topicRepository.deleteById(id);
        logger.info("Topic deleted successfully with ID: {}", id);
    }

    /**
     * Get all topics (flat list).
     */
    public List<TopicDTO> getAllTopics() {
        logger.debug("Fetching all topics");
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
    }
}
