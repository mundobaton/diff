package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.usecases.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class DefaultUploadContent implements UploadContent {

    private GetContent getContent;
    private SaveContent saveContent;
    private UpdateContent updateContent;
    private ProcessDiff processDiff;

    private static final String LEFT = "left";
    private static final String RIGHT = "right";

    @Inject
    public DefaultUploadContent(GetContent getContent, SaveContent saveContent, UpdateContent updateContent, ProcessDiff processDiff) {
        this.getContent = getContent;
        this.saveContent = saveContent;
        this.updateContent = updateContent;
        this.processDiff = processDiff;
    }

    @Override
    public String apply(Model model) {
        try {
            validateModel(model);
        } catch (IllegalArgumentException e) {
            throw new UseCaseException(e.getMessage());
        }

        log.debug("looking for content with id: " + model.getId());
        Optional<Content> optContent = getContent.apply(GetContent.Model.builder().id(model.getId()).build());
        if (!optContent.isPresent()) {
            log.debug("content with id: " + model.getId() + " not found");
            Content content = new Content();
            content.setContentId(model.getId());
            if (model.getSide().equals(LEFT)) {
                content.setLeftContent(model.getData());
            } else {
                content.setRightContent(model.getData());
            }
            saveContent.apply(SaveContent.Model.builder().content(content).build());
            log.debug("saved content with id: " + model.getId());
        } else {
            log.debug("content with id: " + model.getId() + " found!");
            Content content = optContent.get();
            if ((model.getSide().equals(LEFT) && !StringUtils.isBlank(content.getLeftContent())) || (model.getSide().equals(RIGHT) && !StringUtils.isBlank(content.getRightContent()))) {
                log.error("error trying to upload content on a non empty side with content id: " + model.getId());
                throw new ContentAlreadyExistException("There is already content on this side");
            }

            if (model.getSide().equals(LEFT)) {
                content.setLeftContent(model.getData());
            } else {
                content.setRightContent(model.getData());
            }
            updateContent.apply(UpdateContent.Model.builder().content(content).build());
            log.debug("content updated with id: " + model.getId());
            //execute diff in another thread to improve performance and response times
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.submit(() -> processDiff.apply(ProcessDiff.Model.builder().content(content).build()));
            service.shutdown();
        }
        return "content saved successfully";
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

        if (StringUtils.isBlank(model.getSide())) {
            throw new IllegalArgumentException("Side is required");
        }

        if (!model.getSide().equals(LEFT) && !model.getSide().equals(RIGHT)) {
            throw new IllegalArgumentException("Invalid side");
        }

        if (StringUtils.isBlank(model.getData())) {
            throw new IllegalArgumentException("Data is required");
        }
    }
}
