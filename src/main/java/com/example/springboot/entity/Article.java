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
 * 宠物资讯管理
 * </p>
 *
 * @author 
 * @since 
 */
@Data
@ApiModel(value = "Article对象", description = "宠物资讯管理")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("资讯标题")
    @Alias("资讯标题")
    private String name;

    @ApiModelProperty("资讯封面")
    @Alias("资讯封面")
    private String img;

    @ApiModelProperty("资讯详情")
    @Alias("资讯详情")
    private String content;

    @ApiModelProperty("发布时间")
    @Alias("发布时间")
    private String createDate;

    private Integer status;

}
