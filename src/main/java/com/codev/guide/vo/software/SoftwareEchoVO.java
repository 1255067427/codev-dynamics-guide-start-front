package com.codev.guide.vo.software;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SoftwareEchoVO {

    /**
     * 回显上传文件title
     */
    private String title;

    /**
     * 回显上传文件menuName
     */
    private String menuName;

    /**
     * 回显上传文件menuName
     */
    private LocalDate date;

    /**
     * 回显上传文件version
     */
    private String version;

    /**
     * 回显文件note
     */
    private String note;

    /**
     * 回显文件menu
     */
    private String menu;

}
