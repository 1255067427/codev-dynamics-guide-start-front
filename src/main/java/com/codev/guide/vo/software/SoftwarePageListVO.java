package com.codev.guide.vo.software;

import com.codev.guide.entity.Software;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SoftwarePageListVO extends Software {

    /**
     * 与software_menu表id匹配
     */
    @NotNull
    private String typeName;

}
