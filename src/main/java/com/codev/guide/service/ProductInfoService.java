package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.param.PageParam;

import java.net.URL;


public interface ProductInfoService {

    /**
     * 分页查询sheet
     * @param pageParam
     * @return
     */
    IPage<ProductInfo> sheetList(PageParam pageParam);

    /**
     * 根据id查阅文件
     * @param id
     * @return
     */
    URL sheetReference(Long id);

    /**
     * 根据id下载文件
     * @param id
     * @return
     */
    String sheetDownload(Long id);
}
