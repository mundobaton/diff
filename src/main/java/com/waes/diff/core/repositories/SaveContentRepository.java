package com.waes.diff.core.repositories;

import com.waes.diff.core.model.Content;

/**
 * Repository used for saving content
 */
public interface SaveContentRepository {

    /**
     * Saves the content
     * @param content
     */
    void save(Content content);

}
