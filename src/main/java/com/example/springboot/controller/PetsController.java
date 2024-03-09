package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Collect;
import com.example.springboot.service.ICategoryService;
import com.example.springboot.service.ICollectService;
import com.example.springboot.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.service.IPetsService;
import com.example.springboot.entity.Pets;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 宠物管理 前端控制器
 * </p>
 *
 * @author 
 * @since 
 */
@RestController
@RequestMapping("/pets")
public class PetsController {

    @Resource
    private IPetsService petsService;
    @Resource
    private ICollectService collectService;
    @Resource
    private ICategoryService categoryService;
    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Pets pets) {
        if (pets.getId() == null) {

        }
        petsService.saveOrUpdate(pets);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        petsService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        petsService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/collect/page")
    public Result CollectPage(@RequestParam(defaultValue = "") String name,
                                    @RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, TokenUtils.getCurrentUser().getId());
        List<Integer> ids = collectService.list(wrapper).stream().map(Collect::getItemId).collect(Collectors.toList());
        if (ids.size()==0) return Result.success();
        LambdaQueryWrapper<Pets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Pets::getName,name);
        queryWrapper.in(Pets::getId,ids);
        return Result.success(petsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(petsService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(petsService.getById(id));
    }

    @GetMapping("/front/page")
    public Result FrontPage(@RequestParam(defaultValue = "") String name,
                            @RequestParam Integer cateId,

                            @RequestParam Integer pageNum,
                            @RequestParam Integer pageSize) {
        LambdaQueryWrapper<Pets> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(name),Pets::getName,name);
        wrapper.eq(cateId!=0,Pets::getCateId,cateId);
        Page<Pets> page = new Page<>(pageNum, pageSize);
        Page<Pets> result = petsService.page(page, wrapper);
        return Result.success(result);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Pets> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(petsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Pets> list = petsService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Pets信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

        }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Pets> list = reader.readAll(Pets.class);

        petsService.saveBatch(list);
        return Result.success();
    }

}

