package com.example.springboot.controller.resp;

import lombok.Data;

/**
 * @author guo
 * @version 1.0
 * @description: TODO
 * @date 2024/3/9 16:44
 */
@Data
public class CartResp {
    private Integer  id;
    private String goodsName;
    private Integer price;
    private Integer num;
    private Integer goodsId;
    private Integer userId;
    private String img;
}
