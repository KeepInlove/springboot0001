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
 * 宠物分类管理
 * </p>
 *
 * @author 
 * @since 
 */
@Data
@ApiModel(value = "Category对象", description = "宠物分类管理")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("分类名称")
    @Alias("分类名称")
    private String name;
    private Integer status;

}
