package com.codev.guide.param.software;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SoftwareParam {

    /**
     * 文件名
     */
    @NotBlank
    private String title;

    /**
     * oss name
     */
    @NotBlank
    private String name;

    /**
     * 版本号
     */
    @NotBlank
    private String version;

    /**
     * 文件url
     */
    @NotBlank
    private String url;

    /**
     * 版本说明
     */
    private String note;

    /**
     * 类型
     */
    @NotNull
    private Integer type;
}
