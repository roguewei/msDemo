package com.rogue.gbf.gbfdemo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaGoods
 * @date 2019/3/6
 * @params
 * @return
 */
@Data
public class MiaoshaGoods {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
