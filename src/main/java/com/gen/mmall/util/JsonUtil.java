package com.gen.mmall.util;

import com.gen.mmall.pojo.User;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        /*
        对象的所有字段全部列入
        NON_NULL：字段为null不会出现在字符串中
        NON_DEFAULT：字段值为default不会出现在字符串中
        NON_EMPTY：如果字段的类型时String或者集合时，改字段为empty，则不出现在json串中
         */
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        //取消默认转换timestamps形式（即毫秒数的形式：156768387687387）
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //返回没有格式化的json字符串
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    //返回格式化好的json字符串
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    //转换为普通对象
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 转换复杂对象 string2Obj(userListStr, new TypeReference<List<User>>() {});
     * @param str 需要转换的json串
     * @param typeReference 需要转换的复杂类型
     * @param <T>
     * @return
     */
    //转换复杂对象，string2Obj(userListStr, new TypeReference<List<User>>() {});
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 转换复杂对象 string2Obj(userListStr, List.class, User.class);
     * @param str 需要转换的json串
     * @param collectionClass 集合类型，如 List.class Map.class Set.class
     * @param elementClasses 集合中元素的类型
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static void main(String[] args) {

        User u1 = new User();
        u1.setId(1);
        u1.setEmail("geely@happymmall.com");
        u1.setCreateTime(new Date());
        String userJsonPretty = JsonUtil.obj2StringPretty(u1);
        log.info("userJson:{}", userJsonPretty);


        User u2 = new User();
        u2.setId(2);
        u2.setEmail("geelyu2@happymmall.com");


        String user1Json = JsonUtil.obj2String(u1);

        String user1JsonPretty = JsonUtil.obj2StringPretty(u1);

        log.info("user1Json:{}", user1Json);

        log.info("user1JsonPretty:{}", user1JsonPretty);


        User user = JsonUtil.string2Obj(user1Json, User.class);


        List<User> userList = Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        String userListStr = JsonUtil.obj2StringPretty(userList);

        log.info("==================");

        log.info(userListStr);


        List<User> userListObj1 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {});

        List<User> userListObj2 = JsonUtil.string2Obj(userListStr, List.class, User.class);

        System.out.println("end");

    }

}
