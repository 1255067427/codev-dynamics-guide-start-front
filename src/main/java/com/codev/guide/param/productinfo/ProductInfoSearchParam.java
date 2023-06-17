package com.codev.guide.param.productinfo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductInfoSearchParam {

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
