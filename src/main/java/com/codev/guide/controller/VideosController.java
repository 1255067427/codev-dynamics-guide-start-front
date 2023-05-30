package com.codev.guide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.Videos;
import com.codev.guide.param.PageParam;
import com.codev.guide.service.VideosService;
import com.codev.guide.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class VideosController {

    @Autowired
    private VideosService videosService;

    /**
     * 分页查询droneList
     *
     * @param pageParam
     * @return
     */
        @PostMapping("/droneList")
    public R droneList(@RequestBody PageParam pageParam) {

        IPage<Videos> videos = videosService.droneList(pageParam);

        return R.ok(videos);
    }

    /**
     * 根据id放大视频
     */
    @PostMapping("/check")
    public R check(@RequestBody Long id){

        String url = videosService.check(id);

        return R.ok(url);
    }
}
