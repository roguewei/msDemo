package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.rabbitmq.MQReceiver;
import com.rogue.gbf.gbfdemo.rabbitmq.MQSender;
import com.rogue.gbf.gbfdemo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weigaosheng
 * @description
 * @CalssName MQController
 * @date 2019/3/14
 * @params
 * @return
 */
@RestController
@RequestMapping("rabbitmq")
public class MQController {

    @Autowired
    private MQSender mqSender;

    @Autowired
    private MQReceiver mqReceiver;

    @RequestMapping("/mq")
    public Result<String> send(){
        mqSender.send("hello rogue");
        return Result.success("send success");
    }

    @RequestMapping("/topic")
    public Result<String> sendTopic(){
        mqSender.sendTopic("hello rogue");
        return Result.success("send success");
    }

    @RequestMapping("/fanout")
    public Result<String> sendFanout(){
        mqSender.sendFanout("hello rogue");
        return Result.success("send success");
    }

    @RequestMapping("/header")
    public Result<String> sendHeader(){
        mqSender.sendHeader("hello rogue");
        return Result.success("send success");
    }

}
