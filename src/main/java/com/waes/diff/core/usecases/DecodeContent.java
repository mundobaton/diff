package com.waes.diff.core.usecases;

import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

/**
 * This usecase is used for decoding an encoded payload
 */
public interface DecodeContent extends Function<DecodeContent.Model, String> {

    @Builder
    @Getter
    class Model {
        private String content;
    }
}
