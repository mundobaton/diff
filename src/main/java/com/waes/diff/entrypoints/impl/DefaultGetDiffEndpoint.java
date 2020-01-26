package com.waes.diff.entrypoints.impl;

import com.waes.diff.core.dtos.GetDiffDto;
import com.waes.diff.core.model.Content;
import com.waes.diff.core.usecases.GetContent;
import com.waes.diff.entrypoints.BadRequestEndpointException;
import com.waes.diff.entrypoints.ForbiddenEndpointException;
import com.waes.diff.entrypoints.GetDiffEndpoint;
import com.waes.diff.entrypoints.NotFoundEndpointException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class DefaultGetDiffEndpoint implements GetDiffEndpoint {

    private GetContent getContent;

    @Inject
    public DefaultGetDiffEndpoint(GetContent getContent) {
        this.getContent = getContent;
    }

    @Override
    public GetDiffDto execute(RequestModel rm) {
        try {
            validateModel(rm);
        } catch (IllegalArgumentException e) {
            log.error("validation error when performing get diff: " + e.getMessage());
            throw new BadRequestEndpointException(e);
        }

        Optional<Content> optContent = getContent.apply(GetContent.Model.builder().id(Long.parseLong(rm.getId())).build());
        if (!optContent.isPresent()) {
            throw new NotFoundEndpointException("id " + rm.getId() + " not found");
        }

        Content c = optContent.get();
        if (!c.isComplete()) {
            if (StringUtils.isBlank(c.getLeftContent())) {
                throw new ForbiddenEndpointException("left content is missing");
            } else {
                throw new ForbiddenEndpointException("right content is missing");
            }
        }

        return GetDiffDto.builder().result(c.getResult()).build();
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
    }
}
