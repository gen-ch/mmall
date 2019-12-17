package com.gen.mmall.controller;

import com.gen.mmall.dao.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Test {

    private Logger logger = LoggerFactory.getLogger(Test.class);

    @Autowired
    private CartMapper cartMapper;

    @RequestMapping("/test")
    public Map TestAcc(){
        logger.info("info日志测试");
        logger.debug("debug日志测试");
        logger.trace("trace日志测试");
        Map<String,Object> map = new HashMap<>();
        map.put("hello","world");
        map.put("obj",cartMapper.selectByPrimaryKey(126));
        return map;
    }
}
