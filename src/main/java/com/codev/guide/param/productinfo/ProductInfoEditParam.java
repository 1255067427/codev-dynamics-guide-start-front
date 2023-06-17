package com.codev.guide.param.productinfo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductInfoEditParam {

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
