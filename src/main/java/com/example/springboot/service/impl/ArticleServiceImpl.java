package com.example.springboot.service.impl;

import com.example.springboot.entity.Article;
import com.example.springboot.mapper.ArticleMapper;
import com.example.springboot.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物资讯管理 服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
