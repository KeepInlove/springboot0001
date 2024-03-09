package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/9 13:44
 */
@Data
@ApiModel(value = "购物车", description = "购物车")
@TableName("cart")
public class Cart {
    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer goodsId;
    private Integer userId;
    private Integer num;
    private String createTime;
}
