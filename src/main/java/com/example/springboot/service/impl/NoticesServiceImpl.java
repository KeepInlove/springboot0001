package com.example.springboot.service.impl;

import com.example.springboot.entity.Notices;
import com.example.springboot.mapper.NoticesMapper;
import com.example.springboot.service.INoticesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公告管理 服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class NoticesServiceImpl extends ServiceImpl<NoticesMapper, Notices> implements INoticesService {

}
