package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.dao.MiaoshaUserDao;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.exception.GlobalException;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.MiaoshaUserKey;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.service.IMiaoshaUserService;
import com.rogue.gbf.gbfdemo.utils.MD5Util;
import com.rogue.gbf.gbfdemo.utils.UUIDUtil;
import com.rogue.gbf.gbfdemo.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaUserServiceImpl
 * @date 2019/3/5
 * @params
 * @return
 */
@Service
@Slf4j
public class MiaoshaUserServiceImpl implements IMiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    @Override
    public String login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是否存在
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if(miaoshaUser == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDB = miaoshaUser.getSalt();
        // 二次MD5加密处理
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        // 加密后与数据库中的密码进行比较
//        log.info(calcPass+"--------");
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, miaoshaUser);

        return token;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        // 延长有效期
        if(user != null){
            addCookie(response, token, user);
        }
        return user;
    }

    public void addCookie(HttpServletResponse response, String token, MiaoshaUser miaoshaUser){
        redisService.set(MiaoshaUserKey.token, token, miaoshaUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        // 将cookie返回给客户端
        response.addCookie(cookie);
    }
}
