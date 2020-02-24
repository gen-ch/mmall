package com.gen.mmall.controller;

import com.gen.mmall.common.config.RedisConfig;
import com.gen.mmall.dao.CartMapper;
import com.gen.mmall.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Test {

    private Logger logger = LoggerFactory.getLogger(Test.class);

    @Value("${MD5.salt}")
    private String salt;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private RedisConfig redisConfig;

    @RequestMapping("/test")
    public Map TestAcc(){
        logger.info("info日志测试");
        logger.debug("debug日志测试");
        logger.trace("trace日志测试");
        Map<String,Object> map = new HashMap<>();
        map.put("hello","world");
//        map.put("obj",cartMapper.selectByPrimaryKey(126));
//        map.put("md5salt", MD5Util.getSalt());
        map.put("salt", salt);
        map.put("redis属性",redisConfig.getIp());
        return map;
    }
}
