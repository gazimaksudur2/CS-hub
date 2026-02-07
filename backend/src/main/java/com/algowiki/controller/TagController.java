package com.cshub.controller;

import com.cshub.dto.TagDTO;
import com.cshub.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Tag operations.
 */
@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "${cors.allowed-origins}")
@Tag(name = "Tags", description = "API for managing topic tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Get all tags.
     */
    @GetMapping
    @Operation(summary = "Get all tags")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<TagDTO> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    /**
     * Get tag by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get tag by ID")
    public ResponseEntity<TagDTO> getTagById(
            @Parameter(description = "Tag ID") @PathVariable Long id) {
        TagDTO tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    /**
     * Create a new tag.
     */
    @PostMapping
    @Operation(summary = "Create new tag")
    public ResponseEntity<TagDTO> createTag(
            @Parameter(description = "Tag name") @RequestParam String name,
            @Parameter(description = "Tag color (hex code)") @RequestParam(required = false) String color) {
        TagDTO createdTag = tagService.createTag(name, color);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    }

    /**
     * Delete a tag.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tag")
    public ResponseEntity<Void> deleteTag(
            @Parameter(description = "Tag ID") @PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
