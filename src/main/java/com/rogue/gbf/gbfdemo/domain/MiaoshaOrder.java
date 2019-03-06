package com.rogue.gbf.gbfdemo.domain;

import lombok.Data;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaOrder
 * @date 2019/3/6
 * @params
 * @return
 */
@Data
public class MiaoshaOrder {

    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;

}
