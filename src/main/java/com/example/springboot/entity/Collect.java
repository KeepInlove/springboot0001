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
 * 收藏管理
 * </p>
 *
 * @author
 * @since
 */
@Data
@ApiModel(value = "Collect对象", description = "收藏管理")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    @Alias("用户id")
    private Integer userId;

    @ApiModelProperty("收藏id")
    @Alias("收藏id")
    private Integer itemId;
    private Integer status;

}
