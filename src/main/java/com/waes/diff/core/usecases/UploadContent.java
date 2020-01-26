package com.waes.diff.core.usecases;

import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

public interface UploadContent extends Function<UploadContent.Model, String> {

    @Builder
    @Getter
    class Model {
        private Long id;
        private String side;
        private String data;
    }

}
