package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Cart;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.entity.OrderItem;
import com.example.springboot.service.CartService;
import com.example.springboot.service.GoodsService;
import com.example.springboot.service.OrderInfoService;
import com.example.springboot.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:45
 */
@RestController
@RequestMapping("/orderInfo")
@Slf4j
public class OrderInfoController {
    @Resource
    private CartService cartService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderInfoService  orderInfoService;

    @Resource
    private OrderItemService  orderItemService;

    @GetMapping("/findPage")
    public Result adminPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(orderInfoService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer userId,
                            @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("id");
        return Result.success(orderInfoService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("userSave")
    public Result save(@RequestParam Integer userId,
                       @RequestParam String cartIds,
                       @RequestParam Integer addressId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", cartIds);
        List<Cart> list = cartService.list(queryWrapper);
        if (list.size() == 0) {
            return Result.error("-1", "购物车为空");
        }

        //构造订单信息(待插入数据库中的订单信息，待插入数据库中的订单项信息，待删除的购物车信息，待更新的购物车信息，待插入数据库中的订单信息，待插入数据库中的订单项信息，待删除的购物车信息，待更新的购物车信息，待插入数据库中的订单信息，待插入数据库中的订单项信息，待删除的购物车信息，待更新的购物车信息，待插入数据
        OrderInfo  orderInfo = new OrderInfo();
        Integer totalNum = 0;
        BigDecimal totalPrice=new BigDecimal(0);
        HashMap<Integer,Goods> goodsHashMap=new HashMap<>();
        //构造订单信息
        for (Cart cart : list) {
            totalNum += cart.getNum();
            Goods good = goodsService.getById(cart.getGoodsId());
            goodsHashMap.put(cart.getId(),good);
            totalPrice=totalPrice.add(good.getPrice().multiply(new BigDecimal(cart.getNum())));
        }
        orderInfo.setTotalNum(totalNum);
        orderInfo.setTotalAmount(totalPrice);
        orderInfo.setUserId(userId);
        orderInfo.setStatus(Constants.OrderCheckStatus.INIT);
        orderInfo.setAddressId(addressId);
        orderInfoService.saveOrUpdate(orderInfo);
        log.info("orderInfo:{}",orderInfo);
        List<OrderItem> orderItems = new ArrayList<>();
        //构造订单项信息
        for (Cart cart : list) {
            OrderItem orderItem = new OrderItem();
            Goods good = goodsHashMap.get(cart.getId());
            orderItem.setNum(cart.getNum());
            orderItem.setItemPrice(good.getPrice());
            orderItem.setGoodsId(cart.getGoodsId());
            orderItem.setOrderInfoId(orderInfo.getId());
            orderItems.add(orderItem);
        }
        orderItemService.saveBatch(orderItems);
        String[] split = cartIds.split(",");
        for (String s : split) {
            cartService.removeById(Integer.parseInt(s));
        }
        return Result.success(orderInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        cartService.removeById(id);
        return Result.success();
    }

    @GetMapping("pay/{id}")
    public Result pay(@PathVariable Integer id) {
        OrderInfo orderInfo = orderInfoService.getById(id);
        orderInfo.setCheckStatus(Constants.OrderCheckStatus.PAY);
        orderInfoService.saveOrUpdate(orderInfo);
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_info_id", id);
        List<OrderItem> list = orderItemService.list(queryWrapper);
        for (OrderItem orderItem : list) {
            Integer goodsId = orderItem.getGoodsId();
            Goods goods = goodsService.getById(goodsId);
            goods.setId(goodsId);
            goods.setStockNum(goods.getStockNum() - orderItem.getNum());
            goods.setStockOutNum(goods.getStockOutNum() + orderItem.getNum());
            goodsService.saveOrUpdate(goods);
        }
        return Result.success();
    }

    //发货
    @PostMapping("send")
    public Result send(@RequestBody OrderInfo orderInfo) {
        OrderInfo info = orderInfoService.getById(orderInfo.getId());
        info.setCheckStatus(Constants.OrderCheckStatus.EXPRESS);
        info.setExpressNo(orderInfo.getExpressNo());
        orderInfoService.saveOrUpdate(info);
        return Result.success();

    }
}
