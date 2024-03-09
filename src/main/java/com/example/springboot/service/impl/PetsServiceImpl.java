package com.example.springboot.service.impl;

import com.example.springboot.entity.Pets;
import com.example.springboot.mapper.PetsMapper;
import com.example.springboot.service.IPetsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物管理 服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pets> implements IPetsService {

}
