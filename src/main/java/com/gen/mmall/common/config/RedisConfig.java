package com.gen.mmall.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String ip;
    private Integer port;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;
    private Boolean testBorrow;
    private Boolean testReturn;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Boolean getTestBorrow() {
        return testBorrow;
    }

    public void setTestBorrow(Boolean testBorrow) {
        this.testBorrow = testBorrow;
    }

    public Boolean getTestReturn() {
        return testReturn;
    }

    public void setTestReturn(Boolean testReturn) {
        this.testReturn = testReturn;
    }
}
