package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IMiaoshaUserService;
import com.rogue.gbf.gbfdemo.validator.ValidatorUtil;
import com.rogue.gbf.gbfdemo.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author weigaosheng
 * @description
 * @CalssName LoginController
 * @date 2019/3/5
 * @params
 * @return
 */
@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IMiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo){
        log.info(loginVo.toString());
        // 参数校验
        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if(StringUtils.isEmpty(passInput)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }else if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }else if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }

        // 登录
        CodeMsg codeMsg = miaoshaUserService.login(loginVo);
        if(codeMsg.getCode() == 0){
            return Result.success(true);
        }else{
            return Result.error(codeMsg);
        }
    }

}
