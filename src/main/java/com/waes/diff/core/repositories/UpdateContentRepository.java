package com.waes.diff.core.repositories;

import com.waes.diff.core.model.Content;

/**
 * Repository used for updating content
 */
public interface UpdateContentRepository {

    /**
     * Updates the content
     * @param content
     */
    void update(Content content);

}
