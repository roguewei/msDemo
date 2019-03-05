package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.dao.MiaoshaUserDao;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.service.IMiaoshaUserService;
import com.rogue.gbf.gbfdemo.utils.MD5Util;
import com.rogue.gbf.gbfdemo.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaUserServiceImpl
 * @date 2019/3/5
 * @params
 * @return
 */
@Service
public class MiaoshaUserServiceImpl implements IMiaoshaUserService {

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Override
    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    @Override
    public CodeMsg login(LoginVo loginVo) {
        if(loginVo == null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是否存在
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if(miaoshaUser == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        // 验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDB = miaoshaUser.getSalt();
        // 二次MD5加密处理
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        // 加密后与数据库中的密码进行比较
        if(!calcPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
