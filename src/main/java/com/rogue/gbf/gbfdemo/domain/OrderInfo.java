package com.rogue.gbf.gbfdemo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author weigaosheng
 * @description
 * @CalssName OrderInfo
 * @date 2019/3/6
 * @params
 * @return
 */
@Data
public class OrderInfo {

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

}
