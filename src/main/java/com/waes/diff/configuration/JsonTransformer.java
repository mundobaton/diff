package com.waes.diff.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

import javax.inject.Inject;

public class JsonTransformer implements ResponseTransformer {

    private ObjectMapper objectMapper;

    @Inject
    public JsonTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String render(Object model) throws Exception {
        return objectMapper.writeValueAsString(model);
    }
}
