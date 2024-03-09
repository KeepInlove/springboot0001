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
 * 公告管理
 * </p>
 *
 * @author 
 * @since 
 */
@Data
@ApiModel(value = "Notices对象", description = "公告管理")
public class Notices implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @Alias("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公告标题")
    @Alias("公告标题")
    private String name;

    @ApiModelProperty("公告内容")
    @Alias("公告内容")
    private String info;
    private Integer status;


}
