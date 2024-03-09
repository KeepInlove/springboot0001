package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.controller.resp.OrderItemResp;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.OrderItem;
import com.example.springboot.service.GoodsService;
import com.example.springboot.service.OrderItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:45
 */
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Resource
    private OrderItemService orderItemService;

    @Resource
    private GoodsService goodsService;

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") Integer orderInfoId) {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("order_info_id", orderInfoId);
        List<OrderItem> orderItemList = orderItemService.list(queryWrapper);
        List<OrderItemResp> respList = new ArrayList<>();
        if (orderItemList.size() > 0) {
            for (OrderItem record : orderItemList) {
                OrderItemResp resp = new OrderItemResp();
                Goods goods = goodsService.getById(record.getGoodsId());
                resp.setGoodsName(goods.getGoodsName());
                resp.setPrice(goods.getPrice());
                resp.setImg(goods.getImg());
                resp.setNum(record.getNum());
                respList.add(resp);
            }
        }
        return Result.success(respList);
    }


}
