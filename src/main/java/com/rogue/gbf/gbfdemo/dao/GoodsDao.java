package com.rogue.gbf.gbfdemo.dao;

import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 * @description
 * @CalssName GoodsDao
 * @date 2019/3/6
 * @params
 * @return
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();


}
