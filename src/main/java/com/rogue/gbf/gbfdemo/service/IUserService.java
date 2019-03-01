package com.rogue.gbf.gbfdemo.service;

import com.rogue.gbf.gbfdemo.domain.User;

public interface IUserService {
    public User getById(int d);

    /**
     * @return a
     * @Author weigaosheng
     * @Description 测试事务
     * @Date 16:09 2019/3/
     * @Param
     **/
    public boolean getTx(int i);
}
