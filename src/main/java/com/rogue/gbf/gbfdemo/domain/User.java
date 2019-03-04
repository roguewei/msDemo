package com.rogue.gbf.gbfdemo.domain;

import lombok.Data;

/**
 * @author weigaosheng
 * @description
 * @CalssName User
 * @date 2019/3/1
 * @params
 * @return
 */
@Data
public class User {
    private int id;
    private String name;

    public User(){

    }

    public User(int i, String s) {
        this.id = i;
        this.name = s;
    }
}
