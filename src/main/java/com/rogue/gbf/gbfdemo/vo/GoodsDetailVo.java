package com.rogue.gbf.gbfdemo.vo;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import lombok.Data;

/**
 * @author weigaosheng
 * @description
 * @CalssName GoodsDetailVo
 * @date 2019/3/11
 * @params
 * @return
 */
@Data
public class GoodsDetailVo {
    // 秒杀状态
    private int miaoshaStatus = 0;
    // 秒杀剩余时间
    private int remainSeconds = 0;
    // 商品
    private GoodsVo goodsVo;
    // 用户
    private MiaoshaUser miaoshaUser;
}
