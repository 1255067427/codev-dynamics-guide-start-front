package com.codev.guide.param.videos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VideosSearchParam {

    @NotNull
    private Integer pageNum = 1;

    @NotNull
    private Integer pageSize = 10;

    @NotNull
    private Integer type;

    /**
     * title 可以为空
     */
    private String title;
}
