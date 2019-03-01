package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.dao.UserDao;
import com.rogue.gbf.gbfdemo.domain.User;
import com.rogue.gbf.gbfdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * @return a
     * @Author weigaosheng
     * @Description 测试事务
     * @Date 16:09 2019/3/
     * @Param
     **/
    @Override
    @Transactional
    public boolean getTx(int i) {
        User u1 = new User();
        u1.setId(3);
        u1.setName("333");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(4);
        u2.setName("4444");
        userDao.insert(u2);
        return true;
    }
}
