package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.OrderItem;
import com.example.springboot.mapper.OrderItemMapper;
import com.example.springboot.service.OrderItemService;
import org.springframework.stereotype.Service;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:30
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
}
