package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.controller.resp.CartResp;
import com.example.springboot.entity.Cart;

public interface CartService extends IService<Cart> {
    //查询购物车列表
    Page<CartResp> selectCartRespPage(long pageNum,long pageSize,Integer userId);
}
