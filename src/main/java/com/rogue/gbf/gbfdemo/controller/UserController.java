package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IMiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserController
 * @date 2019/3/7
 * @params
 * @return
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IMiaoshaUserService miaoshaUserService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser miaoshaUser){
        return Result.success(miaoshaUser);
    }


}
