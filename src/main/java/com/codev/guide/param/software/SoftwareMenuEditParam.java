package com.codev.guide.param.software;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SoftwareMenuEditParam {

    /**
     * id
     */
    @NotNull
    private Integer id;

    /**
     * title
     */
    @NotEmpty
    private String name;
}
