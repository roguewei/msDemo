package com.rogue.gbf.gbfdemo.service;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.vo.LoginVo;

public interface IMiaoshaUserService {

    public MiaoshaUser getById(long id);

    public CodeMsg login(LoginVo loginVo);
}
