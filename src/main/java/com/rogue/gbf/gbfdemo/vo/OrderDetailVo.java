package com.rogue.gbf.gbfdemo.vo;

import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import lombok.Data;

/**
 * @author weigaosheng
 * @description
 * @CalssName OrderDetailVo
 * @date 2019/3/12
 * @params
 * @return
 */
@Data
public class OrderDetailVo {
    private GoodsVo goodsVo;
    private OrderInfo orderInfo;
}
