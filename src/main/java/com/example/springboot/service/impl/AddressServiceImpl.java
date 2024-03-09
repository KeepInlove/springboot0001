package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Address;
import com.example.springboot.mapper.AddressMapper;
import com.example.springboot.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/6 22:34
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
}
