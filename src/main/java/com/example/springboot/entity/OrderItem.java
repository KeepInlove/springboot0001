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
 * @date 2024/3/6 22:11
 */
@Data
@TableName("order_item")
public class OrderItem {
    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer orderInfoId;
    private Integer goodsId;
    private Integer num;
    private String createTime;
    private BigDecimal itemPrice;
    private Integer status;
}
