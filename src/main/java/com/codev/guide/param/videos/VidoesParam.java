package com.codev.guide.param.videos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VidoesParam {

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
     * 文件url
     */
    @NotBlank
    private String url;

    /**
     * 类型
     */
    @NotNull
    private Integer type;
}
