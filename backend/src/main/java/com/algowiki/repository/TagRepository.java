package com.algowiki.repository;

import com.algowiki.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Tag entity.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find tag by name.
     */
    Optional<Tag> findByName(String name);

    /**
     * Check if tag with name already exists.
     */
    boolean existsByName(String name);
}
