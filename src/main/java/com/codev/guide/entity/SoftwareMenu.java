package com.codev.guide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
public class SoftwareMenu {

    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * name
     */
    @NotBlank
    private String name;

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
     * type：1Drone；2Aviato；3Desktop
     */
    @NotNull
    private Integer type;
}
