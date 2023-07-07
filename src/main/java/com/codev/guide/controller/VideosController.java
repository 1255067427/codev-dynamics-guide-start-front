package com.codev.guide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.entity.Videos;
import com.codev.guide.param.PageParam;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.productinfo.ProductInfoParam;
import com.codev.guide.param.productinfo.ProductInfoSearchParam;
import com.codev.guide.param.videos.VideosEditParam;
import com.codev.guide.param.videos.VideosSearchParam;
import com.codev.guide.param.videos.VideosTypeParam;
import com.codev.guide.param.videos.VidoesParam;
import com.codev.guide.service.VideosService;
import com.codev.guide.utils.AliyunOSSUtils;
import com.codev.guide.utils.R;
import com.codev.guide.vo.productinfo.ProductInfoVO;
import com.codev.guide.vo.videos.VideosVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/videos")
public class VideosController {

    @Autowired
    private VideosService videosService;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    /**
     * 分页查询List
     *
     * @param videoTypeParam
     * @return
     */
    @PostMapping("/list")
    public R droneList(@RequestBody VideosTypeParam videoTypeParam) {

        IPage<Videos> videos = videosService.droneList(videoTypeParam);

        return R.ok(videos);
    }

    /**
     * 根据id放大视频
     */
    @PostMapping("/check")
    public R check(@RequestBody Long id) {

        String url = videosService.check(id);

        return R.ok(url);
    }

    /**
     * 上传videos文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public R upload(@RequestBody MultipartFile file) throws Exception {

        VideosVO videosVO = new VideosVO();
        String originalFilename = file.getOriginalFilename();
        StringBuilder filename = new StringBuilder();
        StringBuilder reverse = filename.append(originalFilename).reverse();
        String substring = reverse.substring(reverse.indexOf(".") + 1);
        StringBuilder title = new StringBuilder().append(substring);
        title.reverse();
        videosVO.setTitle(title.toString());
        originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + originalFilename;
        videosVO.setName(originalFilename);

        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();
        int hours = 1000;

        String url = aliyunOSSUtils.upload(originalFilename, bytes, contentType, hours);
        videosVO.setUrl(url);

        log.info("VideosController.upload([file])业务已结束，结果：{}", videosVO);
        return R.ok(videosVO);
    }

    /**
     * 新增videos
     *
     * @param vidoesParam
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody VidoesParam vidoesParam, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return R.fail("新增失败，参数错误！");
        }

        int result = videosService.add(vidoesParam);

        return R.ok(result);
    }

    /**
     * 根据id回显videos
     *
     * @param id
     * @return
     */
    @PostMapping("/echo")
    public R echo(@RequestBody Long id) {

        Videos videos = videosService.echo(id);

        return R.ok(videos);
    }

    /**
     * 根据id删除productinfo
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long id) {

        int result = videosService.delete(id);

        return R.ok(result);
    }

    /**
     * 根据id修改productinfo
     *
     * @param videosEditParam
     * @return
     */
    @PostMapping("/edit")
    public R edit(@RequestBody VideosEditParam videosEditParam) {

        int result = videosService.edit(videosEditParam);

        return R.ok(result);
    }

    /**
     * 根据title搜索productinfo
     * 为空则查找全部
     *
     * @param videoSearchParam
     * @return
     */
    @PostMapping("/search")
    public R search(@RequestBody VideosSearchParam videoSearchParam) {

        IPage<Videos> result = videosService.search(videoSearchParam);

        return R.ok(result);
    }
}
