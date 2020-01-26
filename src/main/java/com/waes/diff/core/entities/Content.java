package com.waes.diff.core.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Content {

    private Long id;
    private Long contentId;
    private String leftContent;
    private String rightContent;
    private String result;

}
