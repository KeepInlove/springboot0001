package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Comment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findCommentDetail(Integer ordersId);
}
