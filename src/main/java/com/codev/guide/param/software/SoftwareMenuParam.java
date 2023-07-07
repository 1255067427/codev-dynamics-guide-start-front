package com.codev.guide.param.software;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SoftwareMenuParam {

    /**
     * 菜单名
     */
    @NotBlank
    private String name;

    /**
     * 类型
     */
    @NotNull
    private Integer type;
}
