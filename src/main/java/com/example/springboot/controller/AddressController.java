package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Address;
import com.example.springboot.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:45
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer userId,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("user_id", userId);
        return Result.success(addressService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping
    public Result save(@RequestBody Address address) {
        addressService.saveOrUpdate(address);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        addressService.removeById(id);
        return Result.success();
    }
    @GetMapping("findUserAddress/{userId}")
    public Result findUserAddress(@PathVariable Integer userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return Result.success(addressService.list(queryWrapper));
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(addressService.getById(id));
    }
}
