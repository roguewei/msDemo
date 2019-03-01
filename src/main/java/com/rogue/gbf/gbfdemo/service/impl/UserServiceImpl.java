package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.dao.UserDao;
import com.rogue.gbf.gbfdemo.domain.User;
import com.rogue.gbf.gbfdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserServiceImpl
 * @date 2019/3/1
 * @params
 * @return
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }
}
