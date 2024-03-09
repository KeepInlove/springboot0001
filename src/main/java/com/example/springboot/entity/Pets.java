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
 * 宠物管理
 * </p>
 *
 * @author 
 * @since 
 */
@Data
@ApiModel(value = "Pets对象", description = "宠物管理")
public class Pets implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("宠物名称")
    @Alias("宠物名称")
    private String name;

    @ApiModelProperty("宠物封面照")
    @Alias("宠物封面照")
    private String img;

    @ApiModelProperty("宠物详情")
    @Alias("宠物详情")
    private String petsContent;

    @ApiModelProperty("所属分类名称")
    @Alias("所属分类名称")
    private String cateName;

    @ApiModelProperty("所属分类id")
    @Alias("所属分类id")
    private Integer cateId;

    private Integer status;

}
