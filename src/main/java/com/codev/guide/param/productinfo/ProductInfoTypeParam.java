package com.codev.guide.param.productinfo;

import lombok.Data;

@Data
public class ProductInfoTypeParam {


    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer type;
}
