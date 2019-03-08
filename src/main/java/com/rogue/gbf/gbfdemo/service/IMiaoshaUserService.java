package com.rogue.gbf.gbfdemo.service;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface IMiaoshaUserService {

    public MiaoshaUser getById(long id);

    public String login(HttpServletResponse response, LoginVo loginVo);

    public MiaoshaUser getByToken(HttpServletResponse response, String token);
}
