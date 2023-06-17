package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.Software;
import com.codev.guide.param.TypeParam;

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
    String download(Integer id);
}
