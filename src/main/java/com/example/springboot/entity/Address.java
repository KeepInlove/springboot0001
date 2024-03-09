package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:16
 */
@TableName("address")
@Data
public class Address {
    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String address;
    private Integer userId;
    private String phone;
    private String createTime;
    private Integer status;
    private String addressName;
}
