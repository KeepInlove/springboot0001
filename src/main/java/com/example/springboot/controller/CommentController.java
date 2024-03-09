package com.example.springboot.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.config.interceptor.AuthAccess;
import com.example.springboot.entity.Comment;
import com.example.springboot.service.ICommentService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        if (comment.getId() == null) { // 新增评论
            comment.setUserId(TokenUtils.getCurrentUser().getId());
            comment.setTime(DateUtil.now());

            if (comment.getPid() != null) {  // 判断如果是回复，进行处理
                Integer pid = comment.getPid();
                Comment pComment = commentService.getById(pid);
                if (pComment.getOriginId() != null) {  // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    comment.setOriginId(pComment.getOriginId());
                } else {  // 否则就设置父级为当前回复的祖宗
                    comment.setOriginId(comment.getPid());
                }
            }

        }
        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(commentService.list());
    }

    @AuthAccess
    @GetMapping("/tree/{itemId}")
    //评论功能思路
    //1.首先查出该文章下面的所有评论通过左联查询id
    //2.找到所有originID为null的，这样就表示，他已经是最外层的评论了
    //3.现在对每一个最外层评论进行判断处理
    //4.把这条子评论设置为最外层评论的子评论
    //5.对每一个子评论进行判断
    //6.找到当前子评论的父级
    //7.找父级评论的用户id和用户昵称，如果存在就设置给当前的回复对象
    public Result findTree(@PathVariable Integer itemId) {
        //1.首先查出该文章下面的所有评论通过左联查询id
        List<Comment> ordersComments = commentService.findCommentDetail(itemId);
        //2.找到所有originID为null的，这个集合里面都是最外层评论
        List<Comment> originList = ordersComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());
        //3.现在对每一个最外层评论进行判断处理
        for (Comment origin : originList) {
            //4.找出每一个外层评论和子评论ID所对应的子评论
            List<Comment> comments = ordersComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());  // 表示回复对象集合
            //5.对每一个子评论进行判断
            comments.forEach(comment -> {
                //6.找到当前评论的父级
                Optional<Comment> pComment = ordersComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst();
                //7.找到父级评论的用户id和用户昵称，如果存在就设置给当前的回复对象
                pComment.ifPresent((v -> {
                    comment.setPUserId(v.getUserId());
                    comment.setPNickname(v.getNickname());
                }));
            });
            //把填装好数据的子数据设置给最外层评论
            origin.setChildren(comments);
        }
        return Result.success(originList);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(commentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}

