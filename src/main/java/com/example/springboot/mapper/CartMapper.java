package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.controller.resp.CartResp;
import com.example.springboot.entity.Cart;

import java.util.List;

public interface CartMapper extends BaseMapper<Cart> {
    // 定义连表查询方法，返回 CartResp 结果集
    List<CartResp> selectCartRespPage(Integer userId, long pageNum, long pageSize);

}
