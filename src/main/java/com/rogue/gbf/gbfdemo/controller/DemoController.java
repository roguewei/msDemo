package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weigaosheng
 * @description
 * @CalssName DemoController
 * @date 2019/2/28
 * @params
 * @return
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "hello world";
    }

    @RequestMapping("/suc")
    @ResponseBody
    public Result<String> suc(){
        return Result.success("success hello!");
    }

    @RequestMapping("/err")
    @ResponseBody
    public Result<String> err(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "rogue");
        return "hello";
    }
}
