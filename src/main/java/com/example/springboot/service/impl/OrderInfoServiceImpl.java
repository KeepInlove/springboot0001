package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.mapper.OrderInfoMapper;
import com.example.springboot.service.OrderInfoService;
import org.springframework.stereotype.Service;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:35
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements  OrderInfoService{
}
