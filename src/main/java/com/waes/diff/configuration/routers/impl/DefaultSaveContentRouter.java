package com.waes.diff.configuration.routers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.diff.configuration.routers.BaseRouter;
import com.waes.diff.core.dtos.SaveContentDto;
import com.waes.diff.core.exceptions.BadRequestException;
import com.waes.diff.entrypoints.BadRequestEndpointException;
import com.waes.diff.entrypoints.SaveContentEndpoint;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

/**
 * Handles the incoming http request passing a request model to its endpoint.
 * This handler serves for uploading content
 */
@Slf4j
public class DefaultSaveContentRouter implements BaseRouter<SaveContentDto> {

    private static final String ID_PARAM = "id";
    private static final String SIDE_PARAM = "side";
    private static final String DATA_PARAM = "data";
    private ObjectMapper objectMapper;
    private SaveContentEndpoint saveContentEndpoint;

    @Inject
    public DefaultSaveContentRouter(ObjectMapper objectMapper, SaveContentEndpoint saveContentEndpoint) {
        this.objectMapper = objectMapper;
        this.saveContentEndpoint = saveContentEndpoint;
    }

    @Override
    public SaveContentDto execute(Request request, Response response) {
        try {
            Map<String, String> params = objectMapper.readValue(request.body(), Map.class);
            String id = request.params(ID_PARAM);
            String side = request.params(SIDE_PARAM);
            String data = params.get(DATA_PARAM);
            log.debug("performing save content for id: " + id + "and side: " + side);

            return saveContentEndpoint.execute(SaveContentEndpoint.RequestModel.builder().id(id).side(side).data(data).build());
        } catch (IOException | BadRequestEndpointException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
