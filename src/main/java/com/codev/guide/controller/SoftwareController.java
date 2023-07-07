package com.codev.guide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.entity.Software;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.productinfo.ProductInfoSearchParam;
import com.codev.guide.param.software.*;
import com.codev.guide.service.SoftwareMenuService;
import com.codev.guide.service.SoftwareService;
import com.codev.guide.utils.AliyunOSSUtils;
import com.codev.guide.utils.R;
import com.codev.guide.vo.software.SoftwareEchoVO;
import com.codev.guide.vo.software.SoftwareVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    private SoftwareMenuService softwareMenuService;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    /**
     * 根据类型查找 SoftwareMenu
     *
     * @param softwareMenuPageParam
     * @return
     */
    @PostMapping("/menu/list")
    public R menuList(@RequestBody SoftwareMenuPageParam softwareMenuPageParam) {

        IPage<SoftwareMenu> list = softwareMenuService.list(softwareMenuPageParam);

        return R.ok(list);
    }

    /**
     * 根据类型查找 SoftwareMenu
     *
     * @param type
     * @return
     */
    @PostMapping("/menu/front/list")
    public R menuFrontList(@RequestBody Integer type) {

        List<SoftwareMenu> list = softwareMenuService.frontList(type);

        return R.ok(list);
    }

    /**
     * 新增menu
     *
     * @param softwareMenu
     * @return
     */
    @PostMapping("/menu/add")
    public R menuAdd(@RequestBody SoftwareMenuParam softwareMenu, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return R.fail("新增失败，参数错误！");
        }

        int result = softwareMenuService.add(softwareMenu);

        return R.ok(result);
    }

    /**
     * 根据id回显softwareMenu
     *
     * @param id
     * @return
     */
    @PostMapping("/menu/echo")
    public R menuEcho(@RequestBody Long id) {

        SoftwareMenu softwareMenu = softwareMenuService.echo(id);

        return R.ok(softwareMenu);
    }

    /**
     * 根据id修改softwareMenu
     *
     * @param softWareMenuEditParam
     * @return
     */
    @PostMapping("/menu/edit")
    public R edit(@RequestBody SoftwareMenuEditParam softWareMenuEditParam) {

        int result = softwareMenuService.edit(softWareMenuEditParam);

        return R.ok(result);
    }

    /**
     * 根据id删除softwareMenu
     *
     * @param id
     * @return
     */
    @PostMapping("/menu/delete")
    public R delete(@RequestBody Long id) {

        int result = softwareMenuService.delete(id);

        return R.ok(result);
    }

    /**
     * 根据name搜索softwareMenu
     * 为空则查找全部
     *
     * @param softWareMenuSearchParam
     * @return
     */
    @PostMapping("/menu/search")
    public R search(@RequestBody SoftwareMenuSearchParam softWareMenuSearchParam) {

        IPage<SoftwareMenu> result = softwareMenuService.search(softWareMenuSearchParam);

        return R.ok(result);
    }

    /**
     * 根据传入type查找对应software集合
     *
     * @param typeParam
     * @return
     */
    @PostMapping("/list")
    public R softwareList(@RequestBody TypeParam typeParam) {

        IPage<Software> page = softwareService.list(typeParam);

        return R.ok(page);
    }

    /**
     * 根据id返回software url
     *
     * @param id
     * @return
     */
    @PostMapping("/download")
    public R download(@RequestBody Integer id) {

        URL url = softwareService.download(id);

        return R.ok(url);
    }

    /**
     * 上传ProductInfo文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public R upload(@RequestBody MultipartFile file) throws Exception {

        SoftwareVO softwareVO = new SoftwareVO();
        String originalFilename = file.getOriginalFilename();
        StringBuilder filename = new StringBuilder();
        StringBuilder reverse = filename.append(originalFilename).reverse();
        String substring = reverse.substring(reverse.indexOf(".") + 1);
        StringBuilder title = new StringBuilder().append(substring);
        title.reverse();
        softwareVO.setTitle(title.toString());
        originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + originalFilename;
        softwareVO.setName(originalFilename);

        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();
        int hours = 1000;

        String url = aliyunOSSUtils.upload(originalFilename, bytes, contentType, hours);
        softwareVO.setUrl(url);

        log.info("VideosController.upload([file])业务已结束，结果：{}", softwareVO);
        return R.ok(softwareVO);
    }

    /**
     * 新增software
     *
     * @param softwareParam
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody SoftwareParam softwareParam, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return R.fail("新增失败，参数错误！");
        }

        int result = softwareService.add(softwareParam);

        return R.ok(result);
    }

    /**
     * 根据id回显software
     *
     * @param id
     * @return
     */
    @PostMapping("/echo")
    public R echo(@RequestBody Long id) {

        SoftwareEchoVO software = softwareService.echo(id);

        return R.ok(software);
    }

    /**
     * 根据id修改software
     *
     * @param softwareEditParam
     * @return
     */
    @PostMapping("/edit")
    public R edit(@RequestBody SoftwareEditParam softwareEditParam) {

        int result = softwareService.edit(softwareEditParam);

        return R.ok(result);
    }

    /**
     * 根据id删除software
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public R deleteSoftware(@RequestBody Long id) {

        int result = softwareService.delete(id);

        return R.ok(result);
    }

    /**
     * 根据title搜索software
     * 为空则查找全部
     *
     * @param softwareSearchParam
     * @return
     */
    @PostMapping("/search")
    public R searchSoftware(@RequestBody SoftwareSearchParam softwareSearchParam) {

        IPage<Software> result = softwareService.search(softwareSearchParam);

        return R.ok(result);
    }
}
