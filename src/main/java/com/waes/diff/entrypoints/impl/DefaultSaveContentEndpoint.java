package com.waes.diff.entrypoints.impl;

import com.waes.diff.core.dtos.SaveContentDto;
import com.waes.diff.core.usecases.ContentAlreadyExistException;
import com.waes.diff.core.usecases.UploadContent;
import com.waes.diff.entrypoints.BadRequestEndpointException;
import com.waes.diff.entrypoints.SaveContentEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.Objects;

@Slf4j
public class DefaultSaveContentEndpoint implements SaveContentEndpoint {

    private UploadContent uploadContent;

    @Inject
    public DefaultSaveContentEndpoint(UploadContent uploadContent) {
        this.uploadContent = uploadContent;
    }

    @Override
    public SaveContentDto execute(RequestModel rm) {
        try {
            validateModel(rm);
            String resp = uploadContent.apply(UploadContent.Model.builder().id(Long.parseLong(rm.getId())).side(rm.getSide()).data(rm.getData()).build());
            return SaveContentDto.builder().response(resp).build();
        } catch (IllegalArgumentException | ContentAlreadyExistException e) {
            log.error("validation error when performing save content: " + e.getMessage());
            throw new BadRequestEndpointException(e.getMessage());
        }
    }

    private static void validateModel(RequestModel rm) {
        if (Objects.isNull(rm)) {
            throw new IllegalArgumentException("Request model is required");
        }

        if (StringUtils.isBlank(rm.getId())) {
            throw new IllegalArgumentException("Id is required");
        }
        try {
            Long id = Long.parseLong(rm.getId());
            if (id <= 0) {
                throw new IllegalArgumentException("Id must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be numeric");
        }

        if (StringUtils.isBlank(rm.getSide())) {
            throw new IllegalArgumentException("Side is required");
        }

        if (!rm.getSide().equals("right") && !rm.getSide().equals("left")) {
            throw new IllegalArgumentException("Side must be either right or left");
        }

        if (StringUtils.isBlank(rm.getData())) {
            throw new IllegalArgumentException("Data is required");
        }
    }
}
