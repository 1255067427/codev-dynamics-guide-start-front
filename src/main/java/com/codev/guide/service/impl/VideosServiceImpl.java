package com.codev.guide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.entity.Videos;
import com.codev.guide.mapper.VideosMapper;
import com.codev.guide.param.PageParam;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.productinfo.ProductInfoSearchParam;
import com.codev.guide.param.videos.VideosEditParam;
import com.codev.guide.param.videos.VideosSearchParam;
import com.codev.guide.param.videos.VideosTypeParam;
import com.codev.guide.param.videos.VidoesParam;
import com.codev.guide.service.VideosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Slf4j
@Service
public class VideosServiceImpl implements VideosService {

    @Autowired
    private VideosMapper videosMapper;

    /**
     * 分页查询droneList
     *
     * @param videoTypeParam
     * @return
     */
    @Override
    public IPage<Videos> droneList(VideosTypeParam videoTypeParam) {

        IPage<Videos> page = new Page<>(videoTypeParam.getPageNum(), videoTypeParam.getPageSize());
        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", videoTypeParam.getType()).orderByDesc("date_time");

        IPage<Videos> videosPage = videosMapper.selectPage(page, queryWrapper);

        log.info("VideosServiceImpl.droneList([pageParam])业务已结束，结果：{}", videosPage.getRecords());
        return videosPage;
    }

    /**
     * 根据id放大视频
     *
     * @return
     */
    @Override
    public String check(Long id) {

        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Videos videos = videosMapper.selectOne(queryWrapper);
        String url = videos.getUrl();

        videos.setReferenceTimes(videos.getReferenceTimes() + 1);
        videosMapper.updateById(videos);

        log.info("VideosServiceImpl.check([id])业务已结束，结果：{}", url);
        return url;
    }

    /**
     * 新增videos
     *
     * @param vidoesParam
     * @return
     */
    @Override
    public int add(VidoesParam vidoesParam) {

        Videos videos = new Videos();
        videos.setTitle(vidoesParam.getTitle());
        videos.setUrl(vidoesParam.getUrl());
        LocalDate date = LocalDate.now();
        videos.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        videos.setDateTime(dateTime);
        videos.setReferenceTimes(0L);
        videos.setType(vidoesParam.getType());
        videos.setName(vidoesParam.getName());

        int result = videosMapper.insert(videos);

        log.info("VideosServiceImpl.add([vidoesParam])业务已结束，结果：{}", result);
        return result;

    }

    /**
     * 根据id回显videos
     *
     * @param id
     * @return
     */
    @Override
    public Videos echo(Long id) {

        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Videos videos = videosMapper.selectOne(queryWrapper);

        log.info("VideosServiceImpl.echo([id])业务已结束，结果：{}", videos);
        return videos;
    }

    /**
     * 根据id删除productinfo
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {

        int result = videosMapper.deleteById(id);

        log.info("VideosServiceImpl.delete([id])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id修改videos
     *
     * @param videosEditParam
     * @return
     */
    @Override
    public int edit(VideosEditParam videosEditParam) {

        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", videosEditParam.getId());

        Videos videos = videosMapper.selectOne(queryWrapper);
        videos.setTitle(videosEditParam.getTitle());
        LocalDate date = LocalDate.now();
        videos.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        videos.setDateTime(dateTime);

        int result = videosMapper.updateById(videos);

        log.info("VideosServiceImpl.edit([videosEditParam])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据title搜索videos
     * 为空则查找全部
     *
     * @param videoSearchParam
     * @return
     */
    @Override
    public IPage<Videos> search(VideosSearchParam videoSearchParam) {

        IPage<Videos> page = new Page<>(videoSearchParam.getPageNum(), videoSearchParam.getPageSize());
        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();

        if (!videoSearchParam.getTitle().isEmpty() && videoSearchParam.getTitle() != null) {

            queryWrapper.eq("type", videoSearchParam.getType())
                    .like("title", videoSearchParam.getTitle())
                    .orderByDesc("date_time");
            IPage<Videos> videosPage = videosMapper.selectPage(page, queryWrapper);

            log.info("VideosServiceImpl.search([videoSearchParam])业务已结束，结果：{}", videosPage.getRecords());
            return videosPage;
        }

        queryWrapper.eq("type", videoSearchParam.getType())
                .orderByDesc("date_time");
        IPage<Videos> videosPage = videosMapper.selectPage(page, queryWrapper);

        log.info("VideosServiceImpl.search([videoSearchParam])业务已结束，结果：{}", videosPage.getRecords());
        return videosPage;
    }
}
