package com.rogue.gbf.gbfdemo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaUser
 * @date 2019/3/5
 * @params
 * @return
 */
@Data
public class MiaoshaUser {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

}
