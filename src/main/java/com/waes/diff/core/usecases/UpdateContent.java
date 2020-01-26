package com.waes.diff.core.usecases;

import com.waes.diff.core.model.Content;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

/**
 * This usecase updates the content into a repository
 */
public interface UpdateContent extends Function<UpdateContent.Model, Void> {


    @Builder
    @Getter
    class Model {
        private Content content;
    }

}
