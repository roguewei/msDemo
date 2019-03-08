package com.rogue.gbf.gbfdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author weigaosheng
 * @Description 打成jar包可以直接运行
 * @Date 15:32 2019/3/7
 * @Param
 * @return
 **/
@SpringBootApplication
public class GbfdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbfdemoApplication.class, args);
    }

}

/**
 * @Author weigaosheng
 * @Description 打成war包运行的
 * @Date 15:33 2019/3/7
 * @Param
 * @return
 **/
//@SpringBootApplication
//public class GbfdemoApplication extends SpringBootServletInitializer {
//
//    public static void main(String[] args) {
//        SpringApplication.run(GbfdemoApplication.class, args);
//    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(GbfdemoApplication.class);
//    }
//}
