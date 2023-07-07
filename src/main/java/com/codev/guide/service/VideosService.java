package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.Videos;
import com.codev.guide.param.PageParam;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.videos.VideosEditParam;
import com.codev.guide.param.videos.VideosSearchParam;
import com.codev.guide.param.videos.VideosTypeParam;
import com.codev.guide.param.videos.VidoesParam;

public interface VideosService {

    /**
     * 分页查询droneList
     * @param videoTypeParam
     * @return
     */
    IPage<Videos> droneList(VideosTypeParam videoTypeParam);

    /**
     * 根据id放大视频
     * @return
     */
    String check(Long id);

    /**
     * 新增videos
     * @param vidoesParam
     * @return
     */
    int add(VidoesParam vidoesParam);

    /**
     * 根据id回显videos
     * @param id
     * @return
     */
    Videos echo(Long id);

    /**
     * 根据id删除videos
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据id修改videos
     *
     * @param videosEditParam
     * @return
     */
    int edit(VideosEditParam videosEditParam);

    /**
     * 根据title搜索videos
     * @param videoSearchParam
     * @return
     */
    IPage<Videos> search(VideosSearchParam videoSearchParam);
}
