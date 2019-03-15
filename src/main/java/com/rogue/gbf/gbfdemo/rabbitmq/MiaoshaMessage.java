package com.rogue.gbf.gbfdemo.rabbitmq;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import lombok.Data;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaMessage
 * @date 2019/3/15
 * @params
 * @return
 */
@Data
public class MiaoshaMessage {

    private MiaoshaUser user;
    private long goodsId;

}
