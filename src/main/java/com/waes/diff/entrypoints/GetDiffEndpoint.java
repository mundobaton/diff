package com.waes.diff.entrypoints;

import com.waes.diff.core.dtos.GetDiffDto;
import lombok.Builder;
import lombok.Getter;

/**
 * The endpoint avoids coupling between any routing framework and our business logic (usecases).
 * This endpoint handles the get diff operation.
 */
public interface GetDiffEndpoint {

    GetDiffDto execute(RequestModel rm);

    @Builder
    @Getter
    class RequestModel {
        private String id;
    }

}
