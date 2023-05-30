package com.codev.guide.service.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.mapper.ProductInfoMapper;
import com.codev.guide.param.PageParam;
import com.codev.guide.service.ProductInfoService;
import com.codev.guide.utils.AliyunOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    /**
     * 分页查询sheet
     *
     * @param pageParam
     * @return
     */
    @Override
    public IPage<ProductInfo> sheetList(PageParam pageParam) {

        IPage<ProductInfo> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 1);
        IPage<ProductInfo> productInfoEntityIPage = productInfoMapper.selectPage(page, queryWrapper);

        log.info("ProductInfoServiceImpl.sheetList([pageParam])业务已结束，结果：{}", productInfoEntityIPage.getRecords());
        return productInfoEntityIPage;
    }

    /**
     * 根据id查阅文件
     *
     * @param id
     * @return
     */
    @Override
    public URL sheetReference(Long id) {

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ProductInfo productInfo = productInfoMapper.selectOne(queryWrapper);
        String url = productInfo.getUrl();
        String fileName = url.substring(url.lastIndexOf("/") + 1).replace("%20", " ");

        OSS ossClient = new OSSClientBuilder().build(aliyunOSSUtils.getEndPoint(), aliyunOSSUtils.getKeyId(), aliyunOSSUtils.getKeySecret());

        // 验证URL访问是否直接下载。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        GeneratePresignedUrlRequest signRequest = new GeneratePresignedUrlRequest(aliyunOSSUtils.getBucketName(), fileName, HttpMethod.GET);
        signRequest.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(signRequest);

        log.info("ProductInfoServiceImpl.sheetReference([id])业务已结束，结果：{}", signedUrl);
        return signedUrl;
    }

    /**
     * 根据id下载文件
     *
     * @param id
     * @return
     */
    @Override
    public String sheetDownload(Long id) {
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ProductInfo productInfo = productInfoMapper.selectOne(queryWrapper);
        String url = productInfo.getUrl();

        log.info("ProductInfoServiceImpl.sheetDownload([id])业务已结束，结果：{}", url);
        return url;
    }
}
