package com.waes.diff.configuration.routers.impl;

import com.waes.diff.configuration.routers.BaseRouter;
import com.waes.diff.core.dtos.GetDiffDto;
import com.waes.diff.core.exceptions.ForbiddenException;
import com.waes.diff.core.exceptions.NotFoundException;
import com.waes.diff.entrypoints.ForbiddenEndpointException;
import com.waes.diff.entrypoints.GetDiffEndpoint;
import com.waes.diff.entrypoints.NotFoundEndpointException;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

/**
 * Handles the incoming http request passing a request model to its endpoint.
 * This handler serves for getting the diff between contents
 */
@Slf4j
public class DefaultGetDiffRouter implements BaseRouter<GetDiffDto> {

    private static final String ID_PARAM = "id";
    private GetDiffEndpoint getDiffEndpoint;

    @Inject
    public DefaultGetDiffRouter(GetDiffEndpoint getDiffEndpoint) {
        this.getDiffEndpoint = getDiffEndpoint;
    }

    @Override
    public GetDiffDto execute(Request request, Response response) {
        String id = request.params(ID_PARAM);
        log.debug("Performing get diff for id: " + id);
        try {
            return getDiffEndpoint.execute(GetDiffEndpoint.RequestModel.builder().id(id).build());
        } catch (NotFoundEndpointException e) {
            throw new NotFoundException(e.getMessage());
        } catch (ForbiddenEndpointException e) {
            throw new ForbiddenException(e.getMessage());
        }
    }
}
