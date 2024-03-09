package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Goods;
import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:33
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
