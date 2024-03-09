package com.example.springboot.controller.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/10 0:26
 */
@Data
public class OrderItemResp {
    private String goodsName;
    private String img;
    private BigDecimal price;
    private Integer num;
}
