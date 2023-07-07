package com.codev.guide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.productinfo.ProductInfoParam;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.productinfo.ProductInfoSearchParam;
import com.codev.guide.param.productinfo.ProductInfoTypeParam;
import com.codev.guide.service.ProductInfoService;
import com.codev.guide.utils.AliyunOSSUtils;
import com.codev.guide.utils.R;
import com.codev.guide.vo.productinfo.ProductInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    /**
     * 分页查询sheet
     *
     * @param productInfoTypeParam
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody ProductInfoTypeParam productInfoTypeParam) {

        IPage<ProductInfo> list = productInfoService.list(productInfoTypeParam);

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

        String url;
        try {
            url = productInfoService.reference(id);
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
        URL url;
        try {
            url = productInfoService.download(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ProductInfoController.download([id, result])业务已结束，结果：{}", "文件下载失败");
            return R.fail("文件下载失败！", e);
        }

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

        ProductInfoVO productInfoVO = new ProductInfoVO();
        String originalFilename = file.getOriginalFilename();
        StringBuilder filename = new StringBuilder();
        StringBuilder reverse = filename.append(originalFilename).reverse();
        String substring = reverse.substring(reverse.indexOf(".") + 1);
        StringBuilder title = new StringBuilder().append(substring);
        title.reverse();
        productInfoVO.setTitle(title.toString());
        originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + originalFilename;
        productInfoVO.setName(originalFilename);

        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();
        int hours = 1000;

        String url = aliyunOSSUtils.upload(originalFilename, bytes, contentType, hours);
        productInfoVO.setUrl(url);

        log.info("ProductInfoController.upload([file])业务已结束，结果：{}", productInfoVO);
        return R.ok(productInfoVO);
    }

    /**
     * 新增produtinfo
     *
     * @param productInfoParam
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody ProductInfoParam productInfoParam, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return R.fail("新增失败，参数错误！");
        }

        int result = productInfoService.add(productInfoParam);

        return R.ok(result);
    }

    /**
     * 根据id删除productinfo
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long id) {

        int result = productInfoService.delete(id);

        return R.ok(result);
    }

    /**
     * 根据id回显productinfo
     *
     * @param id
     * @return
     */
    @PostMapping("/echo")
    public R echo(@RequestBody Long id) {

        ProductInfo productInfo = productInfoService.echo(id);

        return R.ok(productInfo);
    }

    /**
     * 根据id修改productinfo
     *
     * @param productInfoEditParam
     * @return
     */
    @PostMapping("/edit")
    public R edit(@RequestBody ProductInfoEditParam productInfoEditParam) {

        int result = productInfoService.edit(productInfoEditParam);

        return R.ok(result);
    }

    /**
     * 根据title搜索productinfo
     * 为空则查找全部
     *
     * @param productInfoSearchParam
     * @return
     */
    @PostMapping("/search")
    public R search(@RequestBody ProductInfoSearchParam productInfoSearchParam) {

        IPage<ProductInfo> result = productInfoService.search(productInfoSearchParam);

        return R.ok(result);
    }

}
