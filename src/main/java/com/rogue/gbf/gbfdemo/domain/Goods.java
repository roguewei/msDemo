package com.rogue.gbf.gbfdemo.domain;

import lombok.Data;

/**
 * @author weigaosheng
 * @description
 * @CalssName Goods
 * @date 2019/3/6
 * @params
 * @return
 */
@Data
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

}
