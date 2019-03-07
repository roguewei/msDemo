package com.rogue.gbf.gbfdemo.service;

import com.rogue.gbf.gbfdemo.domain.Goods;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;

import java.util.List;

public interface IGoodsService {
    public List<GoodsVo> listGoodsVo();

    public GoodsVo getGoodsVoByGoodsId(long goodsId);

    public void reduceStock(GoodsVo goods);
}
