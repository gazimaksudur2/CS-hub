package com.cshub.service;

import com.cshub.dto.TopicDTO;
import com.cshub.entity.Tag;
import com.cshub.entity.Topic;
import com.cshub.exception.ResourceNotFoundException;
import com.cshub.mapper.TopicMapper;
import com.cshub.repository.TagRepository;
import com.cshub.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for TopicService.
 * Demonstrates: JUnit 5, Mockito, and proper service layer testing.
 * 
 * Interview talking point: "I wrote unit tests using JUnit and Mockito,
 * mocking repository dependencies to test business logic in isolation."
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TopicService Unit Tests")
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TopicMapper topicMapper;

    @InjectMocks
    private TopicService topicService;

    private Topic sampleTopic;
    private TopicDTO sampleTopicDTO;

    @BeforeEach
    void setUp() {
        // Setup sample data
        sampleTopic = new Topic("Arrays", "arrays", "Content about arrays");
        sampleTopic.setId(1L);

        sampleTopicDTO = new TopicDTO();
        sampleTopicDTO.setId(1L);
        sampleTopicDTO.setTitle("Arrays");
        sampleTopicDTO.setSlug("arrays");
        sampleTopicDTO.setContent("Content about arrays");
    }

    @Test
    @DisplayName("Should get topic by slug successfully")
    void shouldGetTopicBySlugSuccessfully() {
        // Given
        String slug = "arrays";
        when(topicRepository.findBySlug(slug)).thenReturn(Optional.of(sampleTopic));
        when(topicMapper.toDTO(sampleTopic)).thenReturn(sampleTopicDTO);

        // When
        TopicDTO result = topicService.getTopicBySlug(slug);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getSlug()).isEqualTo(slug);
        assertThat(result.getTitle()).isEqualTo("Arrays");
        
        verify(topicRepository, times(1)).findBySlug(slug);
        verify(topicMapper, times(1)).toDTO(sampleTopic);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when topic not found by slug")
    void shouldThrowExceptionWhenTopicNotFoundBySlug() {
        // Given
        String slug = "non-existent";
        when(topicRepository.findBySlug(slug)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> topicService.getTopicBySlug(slug))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Topic")
                .hasMessageContaining("slug")
                .hasMessageContaining(slug);
        
        verify(topicRepository, times(1)).findBySlug(slug);
        verify(topicMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Should get topic tree with proper hierarchy")
    void shouldGetTopicTreeWithProperHierarchy() {
        // Given
        Topic rootTopic = new Topic("DSA", "dsa", "Data Structures");
        rootTopic.setId(1L);
        rootTopic.setDisplayOrder(0);

        Topic childTopic = new Topic("Arrays", "arrays", "Arrays content");
        childTopic.setId(2L);
        childTopic.setParent(rootTopic);
        childTopic.setDisplayOrder(1);

        List<Topic> allTopics = List.of(rootTopic, childTopic);
        
        TopicDTO rootDTO = new TopicDTO(1L, "DSA", "dsa");
        rootDTO.setDisplayOrder(0);
        
        TopicDTO childDTO = new TopicDTO(2L, "Arrays", "arrays");
        childDTO.setParentId(1L);
        childDTO.setDisplayOrder(1);

        when(topicRepository.findAllWithTags()).thenReturn(allTopics);
        when(topicMapper.toDTO(rootTopic)).thenReturn(rootDTO);
        when(topicMapper.toDTO(childTopic)).thenReturn(childDTO);

        // When
        List<TopicDTO> result = topicService.getTopicTree();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1); // Only root topics
        assertThat(result.get(0).getChildren()).hasSize(1); // Child added to root
        
        verify(topicRepository, times(1)).findAllWithTags();
    }

    @Test
    @DisplayName("Should search topics by keyword")
    void shouldSearchTopicsByKeyword() {
        // Given
        String keyword = "array";
        List<Topic> searchResults = List.of(sampleTopic);
        
        when(topicRepository.searchByKeyword(keyword)).thenReturn(searchResults);
        when(topicMapper.toDTO(sampleTopic)).thenReturn(sampleTopicDTO);

        // When
        List<TopicDTO> result = topicService.searchTopics(keyword);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Arrays");
        
        verify(topicRepository, times(1)).searchByKeyword(keyword);
    }

    @Test
    @DisplayName("Should return empty list when searching with empty keyword")
    void shouldReturnEmptyListWhenSearchingWithEmptyKeyword() {
        // When
        List<TopicDTO> result = topicService.searchTopics("");

        // Then
        assertThat(result).isEmpty();
        verify(topicRepository, never()).searchByKeyword(any());
    }

    @Test
    @DisplayName("Should delete topic successfully")
    void shouldDeleteTopicSuccessfully() {
        // Given
        Long topicId = 1L;
        when(topicRepository.existsById(topicId)).thenReturn(true);

        // When
        topicService.deleteTopic(topicId);

        // Then
        verify(topicRepository, times(1)).existsById(topicId);
        verify(topicRepository, times(1)).deleteById(topicId);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent topic")
    void shouldThrowExceptionWhenDeletingNonExistentTopic() {
        // Given
        Long topicId = 999L;
        when(topicRepository.existsById(topicId)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> topicService.deleteTopic(topicId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Topic")
                .hasMessageContaining("id");
        
        verify(topicRepository, times(1)).existsById(topicId);
        verify(topicRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should get topics by tag name")
    void shouldGetTopicsByTagName() {
        // Given
        String tagName = "Array";
        List<Topic> topics = List.of(sampleTopic);
        
        when(topicRepository.findByTagName(tagName)).thenReturn(topics);
        when(topicMapper.toDTO(sampleTopic)).thenReturn(sampleTopicDTO);

        // When
        List<TopicDTO> result = topicService.getTopicsByTag(tagName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        verify(topicRepository, times(1)).findByTagName(tagName);
    }
}

