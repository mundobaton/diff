package com.waes.diff.core.model;

import lombok.Data;

@Data
public class Content {

    private Long contentId;
    private String leftContent;
    private String rightContent;
    private String result;

}
