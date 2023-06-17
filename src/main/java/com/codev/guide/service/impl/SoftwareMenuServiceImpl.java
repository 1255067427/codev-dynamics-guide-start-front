package com.codev.guide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.mapper.SoftwareMenuMapper;
import com.codev.guide.service.SoftwareMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SoftwareMenuServiceImpl implements SoftwareMenuService {

    @Autowired
    private SoftwareMenuMapper softwareMenuMapper;

    /**
     * 根据类型查找 SoftwareMenu
     * @param type
     * @return
     */
    @Override
    public List<SoftwareMenu> list(Integer type) {

        QueryWrapper<SoftwareMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type).orderByAsc("date");

        List<SoftwareMenu> list = softwareMenuMapper.selectList(queryWrapper);

        log.info("SoftwareMenuServiceImpl.list([type])业务已结束，结果：{}",list);
        return list;
    }
}
