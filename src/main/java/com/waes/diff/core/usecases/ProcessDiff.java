package com.waes.diff.core.usecases;

import com.waes.diff.core.model.Content;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

/**
 * This usecase processes diff among contents
 */
public interface ProcessDiff extends Function<ProcessDiff.Model, Void> {

    @Builder
    @Getter
    class Model {
        private Content content;
    }

}
