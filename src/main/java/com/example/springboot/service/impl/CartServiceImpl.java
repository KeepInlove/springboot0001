package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.controller.resp.CartResp;
import com.example.springboot.entity.Cart;
import com.example.springboot.mapper.CartMapper;
import com.example.springboot.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/9 13:47
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Override
    public Page<CartResp> selectCartRespPage(long pageNum,long pageSize,Integer userId) {
        // 调用 CartMapper 的连表查询方法进行分页查询
        List<CartResp> cartResp = baseMapper.selectCartRespPage(userId, pageNum, pageSize);
        Page<CartResp> cartRespPage = new Page<>(pageNum ,pageSize,cartResp.size());
        cartRespPage.setRecords(cartResp);
        return cartRespPage;
    }
}
