package com.codev.guide.service.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ResponseHeaderOverrides;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.mapper.ProductInfoMapper;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.productinfo.ProductInfoParam;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.productinfo.ProductInfoSearchParam;
import com.codev.guide.param.productinfo.ProductInfoTypeParam;
import com.codev.guide.service.ProductInfoService;
import com.codev.guide.utils.AliyunOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
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
     * @param productInfoTypeParam
     * @return
     */
    @Override
    public IPage<ProductInfo> list(ProductInfoTypeParam productInfoTypeParam) {

        IPage<ProductInfo> page = new Page<>(productInfoTypeParam.getPageNum(), productInfoTypeParam.getPageSize());

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", productInfoTypeParam.getType()).orderByDesc("date_time");
        IPage<ProductInfo> productInfoEntityIPage = productInfoMapper.selectPage(page, queryWrapper);

        log.info("ProductInfoServiceImpl.list([pageParam])业务已结束，结果：{}", productInfoEntityIPage.getRecords());
        return productInfoEntityIPage;
    }

    /**
     * 根据id查阅文件
     *
     * @param id
     * @return
     */
    @Override
    public String reference(Long id) {

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ProductInfo productInfo = productInfoMapper.selectOne(queryWrapper);

        // 获取阿里云url
        String url = productInfo.getUrl();
        StringBuilder reverse = new StringBuilder().append(url).reverse();
        String substring = reverse.substring(reverse.indexOf("?") + 1);
        StringBuilder newStringBuilder = new StringBuilder().append(substring);
        String result = newStringBuilder.reverse().toString();

        // 查阅次数+1
        productInfo.setReferenceTimes(productInfo.getReferenceTimes() + 1);
        productInfoMapper.updateById(productInfo);

        log.info("ProductInfoServiceImpl.reference([id])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id下载文件
     *
     * @param id
     * @return
     */
    @Override
    public URL download(Long id) {
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ProductInfo productInfo = productInfoMapper.selectOne(queryWrapper);

        // 获取阿里云url
        OSS ossClient = new OSSClientBuilder().build(aliyunOSSUtils.getEndPoint(), aliyunOSSUtils.getKeyId(), aliyunOSSUtils.getKeySecret());
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        GeneratePresignedUrlRequest signRequest = new GeneratePresignedUrlRequest(aliyunOSSUtils.getBucketName(), productInfo.getName(), HttpMethod.GET);
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        responseHeaders.setContentDisposition("attachment;");
        signRequest.setResponseHeaders(responseHeaders);

        signRequest.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(signRequest);

        // 下载次数+1
        productInfo.setDownloadTimes(productInfo.getDownloadTimes() + 1);
        productInfoMapper.updateById(productInfo);

        log.info("ProductInfoServiceImpl.download([id])业务已结束，结果：{}", signedUrl);
        return signedUrl;
    }

    /**
     * 新增produtinfo
     *
     * @param productInfoParam
     * @return
     */
    @Override
    public int add(ProductInfoParam productInfoParam) {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setTitle(productInfoParam.getTitle());
        productInfo.setUrl(productInfoParam.getUrl());
        LocalDate date = LocalDate.now();
        productInfo.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        productInfo.setDateTime(dateTime);
        productInfo.setReferenceTimes(0L);
        productInfo.setDownloadTimes(0L);
        productInfo.setType(productInfoParam.getType());
        productInfo.setName(productInfoParam.getName());

        int result = productInfoMapper.insert(productInfo);

        log.info("ProductInfoServiceImpl.add([productInfoParam])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id删除productinfo
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {

        int result = productInfoMapper.deleteById(id);

        log.info("ProductInfoServiceImpl.delete([id])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id回显productinfo
     *
     * @param id
     * @return
     */
    @Override
    public ProductInfo echo(Long id) {

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        ProductInfo productInfo = productInfoMapper.selectOne(queryWrapper);

        log.info("ProductInfoServiceImpl.echo([id])业务已结束，结果：{}", productInfo);
        return productInfo;
    }

    /**
     * 根据id修改productinfo
     *
     * @param productInfoEditParam
     * @return
     */
    @Override
    public int edit(ProductInfoEditParam productInfoEditParam) {

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", productInfoEditParam.getId());

        ProductInfo productInfo = productInfoMapper.selectOne(queryWrapper);
        productInfo.setTitle(productInfoEditParam.getTitle());
        LocalDate date = LocalDate.now();
        productInfo.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        productInfo.setDateTime(dateTime);

        int result = productInfoMapper.updateById(productInfo);

        log.info("ProductInfoServiceImpl.edit([productInfoEditParam])业务已结束，结果：{}",result);
        return result;
    }

    /**
     * 根据title搜索productinfo
     * 为空则查找全部
     *
     * @param productInfoSearchParam
     * @return
     */
    @Override
    public IPage<ProductInfo> search(ProductInfoSearchParam productInfoSearchParam) {

        IPage<ProductInfo> page = new Page<>(productInfoSearchParam.getPageNum(), productInfoSearchParam.getPageSize());
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();

        if (!productInfoSearchParam.getTitle().isEmpty() && productInfoSearchParam.getTitle() != null) {

            queryWrapper.eq("type", productInfoSearchParam.getType())
                    .like("title", productInfoSearchParam.getTitle())
                    .orderByDesc("date_time");
            IPage<ProductInfo> productInfoEntityIPage = productInfoMapper.selectPage(page, queryWrapper);

            log.info("ProductInfoServiceImpl.search([productInfoSearchParam])业务已结束，结果：{}", productInfoEntityIPage.getRecords());
            return productInfoEntityIPage;
        }

        queryWrapper.eq("type", productInfoSearchParam.getType())
                .orderByDesc("date_time");
        IPage<ProductInfo> productInfoEntityIPage = productInfoMapper.selectPage(page, queryWrapper);

        log.info("ProductInfoServiceImpl.search([productInfoSearchParam])业务已结束，结果：{}", productInfoEntityIPage.getRecords());
        return productInfoEntityIPage;
    }
}
