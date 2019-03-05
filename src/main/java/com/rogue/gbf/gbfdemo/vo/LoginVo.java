package com.rogue.gbf.gbfdemo.vo;

import com.rogue.gbf.gbfdemo.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author weigaosheng
 * @description
 * @CalssName LoginVo
 * @date 2019/3/5
 * @params
 * @return
 */
@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

}
