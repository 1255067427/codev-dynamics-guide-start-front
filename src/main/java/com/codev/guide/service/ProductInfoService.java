package com.codev.guide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.param.productinfo.ProductInfoEditParam;
import com.codev.guide.param.productinfo.ProductInfoParam;
import com.codev.guide.param.productinfo.ProductInfoSearchParam;
import com.codev.guide.param.productinfo.ProductInfoTypeParam;

import java.net.URL;


public interface ProductInfoService {

    /**
     * 分页查询sheet
     *
     * @param productInfoTypeParam
     * @return
     */
    IPage<ProductInfo> list(ProductInfoTypeParam productInfoTypeParam);

    /**
     * 根据id查阅文件
     *
     * @param id
     * @return
     */
    String reference(Long id);

    /**
     * 根据id下载文件
     *
     * @param id
     * @return
     */
    URL download(Long id);

    /**
     * 新增produtinfo
     *
     * @param productInfoParam
     * @return
     */
    int add(ProductInfoParam productInfoParam);

    /**
     * 根据id删除productinfo
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据id回显productinfo
     *
     * @param id
     * @return
     */
    ProductInfo echo(Long id);

    /**
     * 根据id修改productinfo
     *
     * @param productInfoEditParam
     * @return
     */
    int edit(ProductInfoEditParam productInfoEditParam);

    /**
     * 根据title搜索productinfo
     * 为空则查找全部
     *
     * @param productInfoSearchParam
     * @return
     */
    IPage<ProductInfo> search(ProductInfoSearchParam productInfoSearchParam);
}
