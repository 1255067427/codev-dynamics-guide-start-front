package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.Videos;
import com.codev.guide.param.PageParam;

public interface VideosService {

    /**
     * 分页查询droneList
     * @param pageParam
     * @return
     */
    IPage<Videos> droneList(PageParam pageParam);

    /**
     * 根据id放大视频
     * @return
     */
    String check(Long id);
}
