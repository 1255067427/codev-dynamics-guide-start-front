package com.codev.guide.param.software;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SoftwareSearchParam {

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
