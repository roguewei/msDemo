package com.rogue.gbf.gbfdemo.vo;

import com.rogue.gbf.gbfdemo.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author weigaosheng
 * @description 将商品表和秒杀商品表数据合一
 * @CalssName GoodsVo
 * @date 2019/3/6
 * @params
 * @return
 */
@Data
public class GoodsVo extends Goods {

    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
