package com.codev.guide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class Videos {

    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频title
     */
    @NotBlank
    private String title;

    /**
     * oss name
     */
    @NotBlank
    private String name;

    /**
     * 视频url
     */
    @NotBlank
    private String url;

    /**
     * 上传日期
     */
    @NotNull
    private LocalDate date;

    /**
     * 新增日期
     */
    @NotNull
    private Timestamp dateTime;

    /**
     * 播放次数
     */
    private Long referenceTimes;

    /**
     * 视频类型
     */
    @NotNull
    private Integer type;
}
