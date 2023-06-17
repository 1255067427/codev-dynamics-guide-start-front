package com.codev.guide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codev.guide.entity.Software;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.mapper.SoftwareMapper;
import com.codev.guide.mapper.SoftwareMenuMapper;
import com.codev.guide.param.TypeParam;
import com.codev.guide.service.SoftwareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SoftwareMenuMapper softwareMenuMapper;

    /**
     * 根据传入type查找对应software集合
     *
     * @param typeParam
     * @return
     */
    @Override
    public IPage<Software> list(TypeParam typeParam) {

        IPage<Software> softwarePage = new Page<Software>(typeParam.getPageNum(), typeParam.getPageSize());
        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", typeParam.getType());

        IPage<Software> page = softwareMapper.selectPage(softwarePage, queryWrapper);

        log.info("SoftwareServiceImpl.list([typeParam])业务已结束，结果：{}", page.getRecords());
        return page;
    }

    /**
     * 根据id返回software url
     *
     * @param id
     * @return
     */
    @Override
    public String download(Integer id) {

        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Software software = softwareMapper.selectOne(queryWrapper);
        String url = software.getUrl();

        software.setTimes(software.getTimes() + 1);
        softwareMapper.updateById(software);
        log.info("SoftwareServiceImpl.download([id])业务已结束，结果：{}", url);
        return url;
    }
}
