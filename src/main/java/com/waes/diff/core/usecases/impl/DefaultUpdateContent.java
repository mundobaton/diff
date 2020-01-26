package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.repositories.UpdateContentRepository;
import com.waes.diff.core.usecases.UpdateContent;
import com.waes.diff.core.usecases.UseCaseException;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Objects;

@Slf4j
public class DefaultUpdateContent implements UpdateContent {

    private UpdateContentRepository updateContentRepository;

    @Inject
    public DefaultUpdateContent(UpdateContentRepository updateContentRepository) {
        this.updateContentRepository = updateContentRepository;
    }

    @Override
    public Void apply(Model model) {
        try {
            validateModel(model);
        } catch (IllegalArgumentException e) {
            log.error("Update content error: " + e.getMessage());
            throw new UseCaseException(e.getMessage());
        }

        updateContentRepository.update(model.getContent());
        log.debug("content updated with id: " + model.getContent().getContentId());
        return null;
    }

    private static void validateModel(Model model) {
        if (Objects.isNull(model)) {
            throw new IllegalArgumentException("Model is required");
        }

        if (Objects.isNull(model.getContent())) {
            throw new IllegalArgumentException("Content is required");
        }
    }
}
