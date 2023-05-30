package com.codev.guide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codev.guide.entity.Videos;
import com.codev.guide.mapper.VideosMapper;
import com.codev.guide.param.PageParam;
import com.codev.guide.service.VideosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideosServiceImpl implements VideosService {

    @Autowired
    private VideosMapper videosMapper;

    /**
     * 分页查询droneList
     *
     * @param pageParam
     * @return
     */
    @Override
    public IPage<Videos> droneList(PageParam pageParam) {

        IPage<Videos> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 1);

        IPage<Videos> videosPage = videosMapper.selectPage(page, queryWrapper);

        log.info("VideosServiceImpl.droneList([pageParam])业务已结束，结果：{}", videosPage.getRecords());
        return videosPage;
    }

    /**
     * 根据id放大视频
     * @return
     */
    @Override
    public String check(Long id) {

        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);

        Videos videos = videosMapper.selectOne(queryWrapper);
        String url = videos.getUrl();

        log.info("VideosServiceImpl.check([id])业务已结束，结果：{}",url);
        return url;
    }
}
