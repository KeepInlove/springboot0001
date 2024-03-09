package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:10
 */
@Data
@TableName("order_info")
public class OrderInfo {
    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
//    '订单状态；1待支付，2支付成功，3发货，4订单完
    private Integer checkStatus;
    private Integer status;
    private BigDecimal totalAmount;
    private Integer totalNum;
    private String createTime;
    private Integer addressId;
    //快递单号
    private String expressNo;
}
