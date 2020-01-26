package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.repositories.SaveContentRepository;
import com.waes.diff.core.usecases.SaveContent;
import com.waes.diff.core.usecases.UseCaseException;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Objects;

@Slf4j
public class DefaultSaveContent implements SaveContent {

    private SaveContentRepository saveContentRepository;

    @Inject
    public DefaultSaveContent(SaveContentRepository saveContentRepository) {
        this.saveContentRepository = saveContentRepository;
    }

    @Override
    public Void apply(Model model) {
        try {
            validateModel(model);
        } catch (IllegalArgumentException e) {
            log.error("Save content error: " + e.getMessage());
            throw new UseCaseException(e.getMessage());
        }

        saveContentRepository.save(model.getContent());
        log.debug("Content saved with id: " + model.getContent().getContentId());
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
