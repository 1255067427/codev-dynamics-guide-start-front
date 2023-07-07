package com.codev.guide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codev.guide.entity.ProductInfo;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.mapper.SoftwareMenuMapper;
import com.codev.guide.param.software.SoftwareMenuEditParam;
import com.codev.guide.param.software.SoftwareMenuPageParam;
import com.codev.guide.param.software.SoftwareMenuSearchParam;
import com.codev.guide.param.software.SoftwareMenuParam;
import com.codev.guide.service.SoftwareMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class SoftwareMenuServiceImpl implements SoftwareMenuService {

    @Autowired
    private SoftwareMenuMapper softwareMenuMapper;

    /**
     * SoftwareMenu
     *
     * @param softwareMenuPageParam
     * @return
     */
    @Override
    public IPage<SoftwareMenu> list(SoftwareMenuPageParam softwareMenuPageParam) {

        IPage<SoftwareMenu> page = new Page<>(softwareMenuPageParam.getPageNum(), softwareMenuPageParam.getPageSize());

        QueryWrapper<SoftwareMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("type").orderByDesc("date_time");
        IPage<SoftwareMenu> productInfoEntityIPage = softwareMenuMapper.selectPage(page, queryWrapper);

        log.info("SoftwareMenuServiceImpl.list([type])业务已结束，结果：{}", productInfoEntityIPage.getRecords());
        return productInfoEntityIPage;
    }

    /**
     * 新增menu
     *
     * @param softwareMenu
     * @return
     */
    @Override
    public int add(SoftwareMenuParam softwareMenu) {

        SoftwareMenu menu = new SoftwareMenu();
        menu.setName(softwareMenu.getName());
        LocalDate date = LocalDate.now();
        menu.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        menu.setDateTime(dateTime);
        menu.setType(softwareMenu.getType());

        int result = softwareMenuMapper.insert(menu);

        log.info("SoftwareMenuServiceImpl.add([softwareMenu])业务已结束，结果：{}", result);
        return result;

    }

    /**
     * 根据id回显softwareMenu
     *
     * @param id
     * @return
     */
    @Override
    public SoftwareMenu echo(Long id) {

        QueryWrapper<SoftwareMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        SoftwareMenu softwareMenu = softwareMenuMapper.selectOne(queryWrapper);

        log.info("SoftwareMenuServiceImpl.echo([id])业务已结束，结果：{}", softwareMenu);
        return softwareMenu;
    }

    /**
     * 根据id修改softwaremenu
     *
     * @param softWareMenuEditParam
     * @return
     */
    @Override
    public int edit(SoftwareMenuEditParam softWareMenuEditParam) {

        QueryWrapper<SoftwareMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", softWareMenuEditParam.getId());

        SoftwareMenu softwareMenu = softwareMenuMapper.selectOne(queryWrapper);
        softwareMenu.setName(softWareMenuEditParam.getName());
        LocalDate date = LocalDate.now();
        softwareMenu.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        softwareMenu.setDateTime(dateTime);

        int result = softwareMenuMapper.updateById(softwareMenu);

        log.info("SoftwareMenuServiceImpl.edit([softWareMenuEditParam])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id删除softwareMenu
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {

        int result = softwareMenuMapper.deleteById(id);

        log.info("SoftwareMenuServiceImpl.delete([id])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据title搜索productinfo
     * 为空则查找全部
     *
     * @param softWareMenuSearchParam
     * @return
     */
    @Override
    public IPage<SoftwareMenu> search(SoftwareMenuSearchParam softWareMenuSearchParam) {

        IPage<SoftwareMenu> page = new Page<>(softWareMenuSearchParam.getPageNum(), softWareMenuSearchParam.getPageSize());
        QueryWrapper<SoftwareMenu> queryWrapper = new QueryWrapper<>();

        if (!softWareMenuSearchParam.getName().isEmpty() && softWareMenuSearchParam.getName() != null) {

            queryWrapper.like("name", softWareMenuSearchParam.getName())
                    .orderByAsc("type")
                    .orderByDesc("date_time");
            IPage<SoftwareMenu> softwareMenuIPage = softwareMenuMapper.selectPage(page, queryWrapper);

            log.info("SoftwareMenuServiceImpl.search([softWareMenuSearchParam])业务已结束，结果：{}", softwareMenuIPage);
            return softwareMenuIPage;
        }

        queryWrapper.orderByDesc("date_time");
        IPage<SoftwareMenu> softwareMenuIPage = softwareMenuMapper.selectPage(page, queryWrapper);

        log.info("SoftwareMenuServiceImpl.search([softWareMenuSearchParam])业务已结束，结果：{}", softwareMenuIPage);
        return softwareMenuIPage;
    }

    /**
     * 根据类型查找 SoftwareMenu
     *
     * @param type
     * @return
     */
    @Override
    public List<SoftwareMenu> frontList(Integer type) {

        QueryWrapper<SoftwareMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);

        List<SoftwareMenu> softwareMenus = softwareMenuMapper.selectList(queryWrapper);

        log.info("SoftwareMenuServiceImpl.frontList([type])业务已结束，结果：{}",softwareMenus);
        return softwareMenus;
    }
}
