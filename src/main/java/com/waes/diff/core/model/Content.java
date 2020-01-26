package com.waes.diff.core.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Content {

    private Long contentId;
    private String leftContent;
    private String rightContent;
    private String result;

    public boolean isComplete() {
        return !StringUtils.isBlank(leftContent) && !StringUtils.isBlank(rightContent);
    }

}
