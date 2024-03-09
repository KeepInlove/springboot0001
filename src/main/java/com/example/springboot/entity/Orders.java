package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 预约服务管理
 * </p>
 *
 * @author 
 * @since 
 */
@Data
@ApiModel(value = "Orders对象", description = "预约服务管理")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("预约人id")
    @Alias("预约人id")
    private Integer userId;

    @ApiModelProperty("预约人姓名")
    @Alias("预约人姓名")
    private String userName;

    @ApiModelProperty("预约开始时间")
    @Alias("预约开始时间")
    private String startTime;

    @ApiModelProperty("联系电话")
    @Alias("联系电话")
    private String phone;

    @ApiModelProperty("预约宠物id")
    @Alias("预约宠物id")
    private Integer petsId;

    @ApiModelProperty("预约宠物名")
    @Alias("预约宠物名")
    private String petsName;

    @ApiModelProperty("评价服务")
    @Alias("评价服务")
    private Integer rate;
    @ApiModelProperty("状态")
    @Alias("状态")
    private Integer status;

    @ApiModelProperty("服务")
    @Alias("服务")
    private String employ;

    private Integer checkStatus;

    private String address;

    private String img;


}
