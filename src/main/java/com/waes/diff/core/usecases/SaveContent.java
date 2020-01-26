package com.waes.diff.core.usecases;

import com.waes.diff.core.model.Content;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

/**
 * This usecase saves the content into a repository
 */
public interface SaveContent extends Function<SaveContent.Model, Void> {

    @Builder
    @Getter
    class Model {
        private Content content;
    }
}
