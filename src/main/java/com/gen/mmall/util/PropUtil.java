package com.gen.mmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

public class PropUtil {
    private static final Logger log = LoggerFactory.getLogger(PropUtil.class);

    public static Properties getProperties(String location){
        Properties props = null;
        try {
            log.info("加载资源文件[{}]",location);
            props = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(location),"utf-8"));
        }catch (Exception e){
            log.error("加载资源文件[{}]失败！",location);
            log.error(e.getMessage());
        }
        return props;
    }
}
