package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.usecases.DecodeContent;
import com.waes.diff.core.usecases.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Slf4j
public class DefaultDecodeContent implements DecodeContent {

    @Override
    public String apply(Model model) {
        try {
            validateModel(model);
        } catch (IllegalArgumentException e) {
            log.error("decoding content error: " + e.getMessage());
            throw new UseCaseException(e.getMessage());
        }
        log.debug("Decoding content: " + model.getContent());
        return new String(Base64.decodeBase64(model.getContent().getBytes()));
    }

    private static void validateModel(Model model) {
        if (Objects.isNull(model)) {
            throw new IllegalArgumentException("Model is required");
        }

        if (StringUtils.isBlank(model.getContent())) {
            throw new IllegalArgumentException("Data is required");
        }
    }
}
