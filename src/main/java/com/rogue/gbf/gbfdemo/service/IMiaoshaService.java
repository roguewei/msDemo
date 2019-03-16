package com.rogue.gbf.gbfdemo.service;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;

import java.awt.image.BufferedImage;

public interface IMiaoshaService {
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods);

    public long getMiaoshaResult(Long id, long goodsId);

    public boolean checkPath(MiaoshaUser user, long goodsId, String path);

    public String createMiaoshaPath(MiaoshaUser user, long goodsId);

    public BufferedImage createVerifyCode(MiaoshaUser user, long goodsId);

    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode);
}
