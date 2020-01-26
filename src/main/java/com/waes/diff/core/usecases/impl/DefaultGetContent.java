package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.repositories.FindContentRepository;
import com.waes.diff.core.usecases.GetContent;
import com.waes.diff.core.usecases.UseCaseException;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class DefaultGetContent implements GetContent {

    private FindContentRepository findContentRepository;

    @Inject
    public DefaultGetContent(FindContentRepository findContentRepository) {
        this.findContentRepository = findContentRepository;
    }

    @Override
    public Optional<Content> apply(Model model) {
        try {
            validateModel(model);
        } catch (IllegalArgumentException e) {
            log.error("getting content error: " + e.getMessage());
            throw new UseCaseException(e.getMessage());
        }

        log.debug("looking for content with id: " + model.getId());
        Optional<com.waes.diff.core.entities.Content> optContent = findContentRepository.find(model.getId());
        if (!optContent.isPresent()) {
            log.debug("content not found with id: " + model.getId());
            return Optional.empty();
        }

        com.waes.diff.core.entities.Content c = optContent.get();
        Content content = new Content();
        content.setContentId(c.getContentId());
        content.setLeftContent(c.getLeftContent());
        content.setRightContent(c.getRightContent());
        content.setResult(c.getResult());
        return Optional.of(content);
    }

    private static void validateModel(Model model) {
        if (Objects.isNull(model)) {
            throw new IllegalArgumentException("Model is required");
        }

        if (Objects.isNull(model.getId())) {
            throw new IllegalArgumentException("Id is required");
        }

        if (model.getId() <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }
    }
}
