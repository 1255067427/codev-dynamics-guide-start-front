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
import com.codev.guide.entity.Software;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.mapper.SoftwareMapper;
import com.codev.guide.mapper.SoftwareMenuMapper;
import com.codev.guide.param.TypeParam;
import com.codev.guide.param.software.SoftwareEditParam;
import com.codev.guide.param.software.SoftwareSearchParam;
import com.codev.guide.vo.software.SoftwarePageListVO;
import com.codev.guide.param.software.SoftwareParam;
import com.codev.guide.service.SoftwareService;
import com.codev.guide.utils.AliyunOSSUtils;
import com.codev.guide.vo.software.SoftwareEchoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SoftwareMenuMapper softwareMenuMapper;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    /**
     * 根据传入type查找对应software集合
     *
     * @param typeParam
     * @return
     */
    @Override
    public IPage<Software> list(TypeParam typeParam) {

        QueryWrapper<SoftwareMenu> softwareMenuQueryWrapper = new QueryWrapper<>();
        softwareMenuQueryWrapper.eq("type", typeParam.getMenuType());
        List<SoftwareMenu> softwareMenuList = softwareMenuMapper.selectList(softwareMenuQueryWrapper);
        List<Integer> idList = new ArrayList<>();
        for (SoftwareMenu softwareMenu : softwareMenuList) {
            idList.add(softwareMenu.getId());
        }

        IPage<Software> softwarePage = new Page<>(typeParam.getPageNum(), typeParam.getPageSize());
        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("type", idList).orderByDesc("date_time");
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
    public URL download(Integer id) {

        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Software software = softwareMapper.selectOne(queryWrapper);

        // 获取阿里云url
        OSS ossClient = new OSSClientBuilder().build(aliyunOSSUtils.getEndPoint(), aliyunOSSUtils.getKeyId(), aliyunOSSUtils.getKeySecret());
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        GeneratePresignedUrlRequest signRequest = new GeneratePresignedUrlRequest(aliyunOSSUtils.getBucketName(), software.getName(), HttpMethod.GET);
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        responseHeaders.setContentDisposition("attachment;");
        signRequest.setResponseHeaders(responseHeaders);

        signRequest.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(signRequest);

        software.setDownloadTimes(software.getDownloadTimes() + 1);
        softwareMapper.updateById(software);

        log.info("SoftwareServiceImpl.download([id])业务已结束，结果：{}", signedUrl);
        return signedUrl;
    }

    /**
     * 新增software
     *
     * @param softwareParam
     * @return
     */
    @Override
    public int add(SoftwareParam softwareParam) {
        Software software = new Software();
        software.setTitle(softwareParam.getTitle());
        software.setUrl(softwareParam.getUrl());
        LocalDate date = LocalDate.now();
        software.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        software.setDateTime(dateTime);
        software.setDownloadTimes(0L);
        software.setName(softwareParam.getName());
        software.setVersion(softwareParam.getVersion());
        software.setNote(softwareParam.getNote());
        software.setType(softwareParam.getType());

        QueryWrapper<SoftwareMenu> softwareMenuQueryWrapper = new QueryWrapper<>();
        softwareMenuQueryWrapper.eq("id", softwareParam.getType());
        SoftwareMenu softwareMenu = softwareMenuMapper.selectOne(softwareMenuQueryWrapper);
        software.setTypeName(softwareMenu.getName());

        int result = softwareMapper.insert(software);

        log.info("SoftwareServiceImpl.add([softwareParam])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id回显software
     *
     * @param id
     * @return
     */
    @Override
    public SoftwareEchoVO echo(Long id) {

        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        Software software = softwareMapper.selectOne(queryWrapper);

        QueryWrapper<SoftwareMenu> softwareMenuQueryWrapper = new QueryWrapper<>();
        softwareMenuQueryWrapper.eq("id", software.getType());
        String name = softwareMenuMapper.selectOne(softwareMenuQueryWrapper).getName();

        SoftwareEchoVO softwareEchoVO = new SoftwareEchoVO();
        softwareEchoVO.setTitle(software.getTitle());
        softwareEchoVO.setNote(software.getNote());
        softwareEchoVO.setVersion(software.getVersion());
        softwareEchoVO.setMenuName(name);
        softwareEchoVO.setDate(software.getDate());

        log.info("ProductInfoServiceImpl.echo([id])业务已结束，结果：{}", softwareEchoVO);
        return softwareEchoVO;
    }

    /**
     * 根据id修改software
     *
     * @param softwareEditParam
     * @return
     */
    @Override
    public int edit(SoftwareEditParam softwareEditParam) {

        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", softwareEditParam.getId());

        Software software = softwareMapper.selectOne(queryWrapper);
        software.setTitle(softwareEditParam.getTitle());
        LocalDate date = LocalDate.now();
        software.setDate(date);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        software.setDateTime(dateTime);
        software.setVersion(softwareEditParam.getVersion());
        software.setNote(softwareEditParam.getNote());
        software.setType(softwareEditParam.getType());
        software.setTypeName(softwareEditParam.getMenuName());

        int result = softwareMapper.updateById(software);

        log.info("SoftwareServiceImpl.edit([softwareEditParam])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据id删除software
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {

        int result = softwareMapper.deleteById(id);

        log.info("SoftwareServiceImpl.delete([id])业务已结束，结果：{}", result);
        return result;
    }

    /**
     * 根据title搜索software
     * 为空则查找全部
     *
     * @param softwareSearchParam
     * @return
     */
    @Override
    public IPage<Software> search(SoftwareSearchParam softwareSearchParam) {

        QueryWrapper<SoftwareMenu> softwareMenuQueryWrapper = new QueryWrapper<>();
        softwareMenuQueryWrapper.eq("type", softwareSearchParam.getType());
        List<SoftwareMenu> softwareMenuList = softwareMenuMapper.selectList(softwareMenuQueryWrapper);
        List<Integer> idList = new ArrayList<>();
        for (SoftwareMenu softwareMenu : softwareMenuList) {
            idList.add(softwareMenu.getId());
        }

        IPage<Software> page = new Page<>(softwareSearchParam.getPageNum(), softwareSearchParam.getPageSize());
        QueryWrapper<Software> queryWrapper = new QueryWrapper<>();

        if (!softwareSearchParam.getTitle().isEmpty() && softwareSearchParam.getTitle() != null) {

            queryWrapper.in("type", idList)
                    .like("title", softwareSearchParam.getTitle())
                    .orderByDesc("date_time");
            IPage<Software> softwareIPage = softwareMapper.selectPage(page, queryWrapper);

            log.info("SoftwareServiceImpl.search([softwareSearchParam])业务已结束，结果：{}", softwareIPage.getRecords());
            return softwareIPage;
        }

        queryWrapper.in("type", idList)
                .orderByDesc("date_time");
        IPage<Software> softwareIPage = softwareMapper.selectPage(page, queryWrapper);

        log.info("SoftwareServiceImpl.search([softwareSearchParam])业务已结束，结果：{}", softwareIPage.getRecords());
        return softwareIPage;
    }
}
