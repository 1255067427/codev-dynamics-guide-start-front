package com.codev.guide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
public class Software {

    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * title
     */
    @NotBlank
    private String title;

    /**
     * url
     */
    @NotBlank
    private String url;

    /**
     * 版本号
     */
    @NotBlank
    private String version;

    /**
     * 上传日期
     */
    @NotNull
    private LocalDate date;

    /**
     * 版本说明
     */
    private String note;

    /**
     * 下载次数
     */
    private Long times;

    /**
     * 与software_menu表id匹配
     */
    @NotNull
    private Integer type;
}
