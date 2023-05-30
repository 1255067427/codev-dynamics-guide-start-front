package com.codev.guide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.param.PageParam;
import com.codev.guide.service.ProductInfoService;
import com.codev.guide.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@Slf4j
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 分页查询sheet
     *
     * @param pageParam
     * @return
     */
    @PostMapping("/sheetList")
    public R sheetList(@RequestBody PageParam pageParam) {

        IPage<ProductInfo> list = productInfoService.sheetList(pageParam);

        return R.ok(list);
    }

    /**
     * 根据id查阅文件
     *
     * @param id
     * @return
     */
    @PostMapping("/reference")
    public R reference(@RequestBody Long id) {

        URL url;
        try {
            url = productInfoService.sheetReference(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ProductInfoController.reference([id, result])业务已结束，结果：{}", "文件下载失败");
            return R.fail("文件下载失败！", e);
        }

        return R.ok(url);
    }

    /**
     * 根据id下载文件
     *
     * @param id
     * @param result
     * @return
     */
    @PostMapping("/download")
    public R download(@RequestBody Long id, BindingResult result) {

        if (result.hasErrors()) {

            return R.fail("查阅失败，参数错误！");
        }
        String url;
        try {
            url = productInfoService.sheetDownload(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ProductInfoController.download([id, result])业务已结束，结果：{}", "文件下载失败");
            return R.fail("文件下载失败！", e);
        }

        return R.ok(url);
    }
}
