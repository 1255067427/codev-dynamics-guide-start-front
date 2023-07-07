package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.entity.Software;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.software.SoftwareEditParam;
import com.codev.guide.param.software.SoftwareParam;
import com.codev.guide.param.software.SoftwareSearchParam;
import com.codev.guide.vo.software.SoftwareEchoVO;

import java.net.URL;

public interface SoftwareService {

    /**
     * 根据传入type查找对应software集合
     * @param typeParam
     * @return
     */
    IPage<Software> list(TypeParam typeParam);

    /**
     * 根据id返回software url
     * @param id
     * @return
     */
    URL download(Integer id);

    /**
     * 新增software
     *
     * @param softwareParam
     * @return
     */
    int add(SoftwareParam softwareParam);

    /**
     * 根据id回显software
     *
     * @param id
     * @return
     */
    SoftwareEchoVO echo(Long id);

    /**
     * 根据id修改software
     *
     * @param softwareEditParam
     * @return
     */
    int edit(SoftwareEditParam softwareEditParam);

    /**
     * 根据id删除software
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据title搜索software
     * 为空则查找全部
     *
     * @param softwareSearchParam
     * @return
     */
    IPage<Software> search(SoftwareSearchParam softwareSearchParam);
}
