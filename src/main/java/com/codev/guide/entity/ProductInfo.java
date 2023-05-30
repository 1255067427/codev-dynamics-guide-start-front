package com.codev.guide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ProductInfo {

    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    @NotEmpty
    private String title;

    /**
     * 文件url
     */
    @NotEmpty
    private String url;

    /**
     * 上传日期
     */
    @NotNull
    private LocalDate date;

    /**
     * 查看次数
     */
    private Long referenceTimes = 0L;

    /**
     * 下载次数
     */
    private Long downloadTimes = 0L;

    /**
     * 类型
     */
    private Integer type;

}
