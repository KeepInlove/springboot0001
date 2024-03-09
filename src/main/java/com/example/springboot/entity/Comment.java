package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论人id
     */
    private Integer userId;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 父id
     */
    private Integer pid;
    @TableField(exist = false)
    private String pNickname;  // 父节点的用户昵称
    @TableField(exist = false)
    private Integer pUserId;  // 父节点的用户id

    /**
     * 最上级评论id
     */
    private Integer originId;

    /**
     * 关联id
     */
    private Integer itemId;

    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private List<Comment> children;

    private Integer status;
}
