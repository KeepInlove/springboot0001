package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Cart;
import com.example.springboot.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:45
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer userId,
                            @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("id");
        return Result.success(cartService.selectCartRespPage(pageNum,pageSize,userId));
    }

    @PostMapping
    public Result save(@RequestBody Cart cart) {
        if (null==cart||cart.getNum()==null){
            return Result.error("-1","添加商品参数错误");
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", cart.getGoodsId());
        queryWrapper.eq("user_id", cart.getUserId());
        Cart one = cartService.getOne(queryWrapper);
        if (one != null) {
            cart.setId(one.getId());
            cart.setNum(one.getNum() + cart.getNum());
        }
        cartService.saveOrUpdate(cart);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        cartService.removeById(id);
        return Result.success();
    }

}
