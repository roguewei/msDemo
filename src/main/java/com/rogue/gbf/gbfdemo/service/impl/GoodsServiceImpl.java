package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.dao.GoodsDao;
import com.rogue.gbf.gbfdemo.dao.UserDao;
import com.rogue.gbf.gbfdemo.domain.Goods;
import com.rogue.gbf.gbfdemo.domain.User;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IUserService;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserServiceImpl
 * @date 2019/3/1
 * @params
 * @return
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

}
