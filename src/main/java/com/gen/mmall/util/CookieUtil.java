package com.gen.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final Logger log = LoggerFactory.getLogger(CookieUtil.class);

    //cookie保存在该域名下，xxx.gen.com 均可读取该cookie
    //同级之间不可获取，如 aa.gen.com 和 bb.gen.com 之间的 cookie 不共享，setPath 方法同理
    //tomcat高于8.5版本，域名前不加“.”  低于8.5版本，域名前需要加点，即 ".gen.com"
    private final static String COOKIE_DOMAIN = "gen.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //X:domain=".happymmall.com"
    //a:A.happymmall.com            cookie:domain=A.happymmall.com; path="/"
    //b:B.happymmall.com            cookie:domain=B.happymmall.com; path="/"
    //c:A.happymmall.com/test/cc    cookie:domain=A.happymmall.com; path="/test/cc"
    //d:A.happymmall.com/test/dd    cookie:domain=A.happymmall.com; path="/test/dd"
    //e:A.happymmall.com/test       cookie:domain=A.happymmall.com; path="/test"

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        //设置访问路径，${COOKIE_DOMAIN}/{path}/... 均可读取该cookie
        ck.setPath("/");//代表设置在根目录，
        ck.setHttpOnly(true);
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//设置有效期，单位是秒，如果是-1，代表永久
        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此cookie。
                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
