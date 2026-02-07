package com.algowiki.repository;

import com.algowiki.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Topic entity.
 * Spring Data JPA automatically implements basic CRUD operations.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Find topic by slug.
     * Used for pretty URLs like /dsa/arrays
     */
    Optional<Topic> findBySlug(String slug);

    /**
     * Find all root topics (topics without parent).
     * This is the starting point for building the tree hierarchy.
     */
    List<Topic> findByParentIsNull();

    /**
     * Find all children of a parent topic.
     */
    List<Topic> findByParentId(Long parentId);

    /**
     * Find all topics by tag name.
     * Demonstrates DBMS: Querying Many-to-Many relationship.
     */
    @Query("SELECT t FROM Topic t JOIN t.tags tag WHERE tag.name = :tagName")
    List<Topic> findByTagName(@Param("tagName") String tagName);

    /**
     * Search topics by title or content.
     * Used for the search bar feature.
     * Using ILIKE for case-insensitive search (PostgreSQL specific).
     */
    @Query("SELECT t FROM Topic t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(t.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Topic> searchByKeyword(@Param("keyword") String keyword);

    /**
     * Fetch all topics with their tags in a single query.
     * Solves the N+1 select problem using JOIN FETCH.
     * This is the "killer feature" mentioned in the guide!
     */
    @Query("SELECT DISTINCT t FROM Topic t LEFT JOIN FETCH t.tags")
    List<Topic> findAllWithTags();

    /**
     * Check if slug already exists (for validation).
     */
    boolean existsBySlug(String slug);
}
