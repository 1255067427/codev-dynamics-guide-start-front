package com.codev.guide.service;

import com.codev.guide.entity.SoftwareMenu;

import java.util.List;

public interface SoftwareMenuService {

    /**
     * 根据类型查找 SoftwareMenu
     * @param type
     * @return
     */
    List<SoftwareMenu> list(Integer type);
}
