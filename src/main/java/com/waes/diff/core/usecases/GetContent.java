package com.waes.diff.core.usecases;

import com.waes.diff.core.model.Content;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Function;

/**
 * This usecase is used for retrieving content from a repository
 */
public interface GetContent extends Function<GetContent.Model, Optional<Content>> {

    @Builder
    @Getter
    class Model {
        private Long id;
    }

}
