package com.waes.diff.entrypoints;

import com.waes.diff.core.dtos.SaveContentDto;
import lombok.Builder;
import lombok.Getter;

/**
 * The endpoint avoids coupling between any routing framework and our business logic (usecases).
 * This endpoint handles the save diff operation.
 */
public interface SaveContentEndpoint {

    SaveContentDto execute(RequestModel rm);

    @Builder
    @Getter
    class RequestModel {
        private String id;
        private String side;
        private String data;
    }

}
