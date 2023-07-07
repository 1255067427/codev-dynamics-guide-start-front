package com.codev.guide.param.software;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SoftwareEditParam {

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

    /**
     * 版本号
     */
    @NotBlank
    private String version;

    /**
     * 版本说明
     */
    private String note;

    /**
     * 与software_menu表id匹配
     */
    @NotNull
    private Integer type;

    /**
     * 与software_menu表id匹配的menuName
     */
    @NotNull
    private String menuName;
}
