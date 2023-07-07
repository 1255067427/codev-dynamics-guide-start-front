package com.codev.guide.param.videos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VideosEditParam {

    /**
     * id
     */
    @NotNull
    private Integer id;

    /**
     * title
     */
    @NotEmpty
    private String title;
}
