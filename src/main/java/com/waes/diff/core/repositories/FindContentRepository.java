package com.waes.diff.core.repositories;

import com.waes.diff.core.entities.Content;

import java.util.Optional;

/**
 * Repository used for finding content
 */
public interface FindContentRepository {

    /**
     * Finds a content by contentId
     * @param contentId
     * @return the content
     */
    Optional<Content> find(Long contentId);

}
