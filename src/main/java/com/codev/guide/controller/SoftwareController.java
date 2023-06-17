package com.codev.guide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codev.guide.entity.Software;
import com.codev.guide.entity.SoftwareMenu;
import com.codev.guide.param.TypeParam;
import com.codev.guide.service.SoftwareMenuService;
import com.codev.guide.service.SoftwareService;
import com.codev.guide.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    private SoftwareMenuService softwareMenuService;

    @Autowired
    private SoftwareService softwareService;

    /**
     * 根据类型查找 SoftwareMenu
     *
     * @param type
     * @return
     */
    @PostMapping("/menu/list")
    public R menuList(@RequestBody Integer type) {

        List<SoftwareMenu> list = softwareMenuService.list(type);

        return R.ok(list);
    }

    /**
     * 根据传入type查找对应software集合
     *
     * @param typeParam
     * @return
     */
    @PostMapping("/list")
    public R softwareList(@RequestBody TypeParam typeParam) {

        IPage<Software> page = softwareService.list(typeParam);

        return R.ok(page);
    }

    /**
     * 根据id返回software url
     * @param id
     * @return
     */
    @PostMapping("/download")
    public R download(@RequestBody Integer id){

        String url = softwareService.download(id);

        return R.ok(url);
    }
}
