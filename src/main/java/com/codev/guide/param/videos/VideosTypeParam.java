package com.codev.guide.param.videos;

import lombok.Data;

@Data
public class VideosTypeParam {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer type;
}
