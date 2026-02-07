package com.cshub.controller;

import com.cshub.dto.CreateTopicRequest;
import com.cshub.dto.TopicDTO;
import com.cshub.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Topic operations.
 * Demonstrates: Proper use of ResponseEntity with HTTP status codes.
 */
@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "${cors.allowed-origins}")
@Tag(name = "Topics", description = "API for managing documentation topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Get all topics organized in a tree structure.
     * This is the main endpoint for the sidebar navigation.
     */
    @GetMapping("/tree")
    @Operation(summary = "Get topic tree", description = "Returns all topics organized in hierarchical tree structure")
    public ResponseEntity<List<TopicDTO>> getTopicTree() {
        List<TopicDTO> tree = topicService.getTopicTree();
        return ResponseEntity.ok(tree);
    }

    /**
     * Get all topics (flat list).
     */
    @GetMapping
    @Operation(summary = "Get all topics", description = "Returns all topics as a flat list")
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    /**
     * Get a single topic by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get topic by ID")
    public ResponseEntity<TopicDTO> getTopicById(
            @Parameter(description = "Topic ID") @PathVariable Long id) {
        TopicDTO topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    /**
     * Get a topic by slug (for pretty URLs).
     */
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get topic by slug", description = "Retrieve topic using URL-friendly slug")
    public ResponseEntity<TopicDTO> getTopicBySlug(
            @Parameter(description = "Topic slug", example = "dsa-arrays") @PathVariable String slug) {
        TopicDTO topic = topicService.getTopicBySlug(slug);
        return ResponseEntity.ok(topic);
    }

    /**
     * Search topics by keyword.
     * Used by the search bar feature.
     */
    @GetMapping("/search")
    @Operation(summary = "Search topics", description = "Search topics by keyword in title or content")
    public ResponseEntity<List<TopicDTO>> searchTopics(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<TopicDTO> topics = topicService.searchTopics(keyword);
        return ResponseEntity.ok(topics);
    }

    /**
     * Get topics by tag name.
     */
    @GetMapping("/tag/{tagName}")
    @Operation(summary = "Get topics by tag")
    public ResponseEntity<List<TopicDTO>> getTopicsByTag(
            @Parameter(description = "Tag name", example = "Array") @PathVariable String tagName) {
        List<TopicDTO> topics = topicService.getTopicsByTag(tagName);
        return ResponseEntity.ok(topics);
    }

    /**
     * Create a new topic.
     */
    @PostMapping
    @Operation(summary = "Create new topic")
    public ResponseEntity<TopicDTO> createTopic(@Valid @RequestBody CreateTopicRequest request) {
        TopicDTO createdTopic = topicService.createTopic(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    /**
     * Update an existing topic.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update topic")
    public ResponseEntity<TopicDTO> updateTopic(
            @Parameter(description = "Topic ID") @PathVariable Long id,
            @Valid @RequestBody CreateTopicRequest request) {
        TopicDTO updatedTopic = topicService.updateTopic(id, request);
        return ResponseEntity.ok(updatedTopic);
    }

    /**
     * Delete a topic.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete topic")
    public ResponseEntity<Void> deleteTopic(
            @Parameter(description = "Topic ID") @PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}

