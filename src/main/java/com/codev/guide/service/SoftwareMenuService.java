package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.param.software.SoftwareMenuEditParam;
import com.codev.guide.param.software.SoftwareMenuPageParam;
import com.codev.guide.param.software.SoftwareMenuSearchParam;
import com.codev.guide.param.software.SoftwareMenuParam;

import java.util.List;

public interface SoftwareMenuService {

    /**
     *  SoftwareMenu
     *
     * @param softwareMenuPageParam
     * @return
     */
    IPage<SoftwareMenu> list(SoftwareMenuPageParam softwareMenuPageParam);

    /**
     * 新增menu
     *
     * @param softwareMenu
     * @return
     */
    int add(SoftwareMenuParam softwareMenu);

    /**
     * 根据id回显softwareMenu
     *
     * @param id
     * @return
     */
    SoftwareMenu echo(Long id);

    /**
     * 根据id修改softwareMenu
     *
     * @param softWareMenuEditParam
     * @return
     */
    int edit(SoftwareMenuEditParam softWareMenuEditParam);

    /**
     * 根据id删除softwareMenu
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据name搜索softwareMenu
     * 为空则查找全部
     *
     * @param softWareMenuSearchParam
     * @return
     */
    IPage<SoftwareMenu> search(SoftwareMenuSearchParam softWareMenuSearchParam);

    /**
     * 根据类型查找 SoftwareMenu
     * @param type
     * @return
     */
    List<SoftwareMenu> frontList(Integer type);
}
