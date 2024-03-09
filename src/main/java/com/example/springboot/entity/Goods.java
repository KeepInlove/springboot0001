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
 * @date 2024/3/6 22:08
 */
@Data
@TableName("goods")
public class Goods {
    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String goodsName;
    private Integer stockNum;
    private String img;
    private String des;
    private String createTime;
    private Integer status;
    private Integer checkStatus;
    private Integer stockOutNum;
    private BigDecimal price;
}
