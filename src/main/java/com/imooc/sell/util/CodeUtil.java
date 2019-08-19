package com.imooc.sell.util;



import java.util.Random;

/**
 * 编码工具类
 * @author lei
 */
public class CodeUtil {
    /**
     * 生成唯一编码 格式：时间+随机数
     * @return
     */
    public static synchronized String uniqueCode() {
        long millis = System.currentTimeMillis();
        // 生成1000000以内的随机整数
        int anInt = new Random().nextInt(900000) + 100000;
        return  millis + String.valueOf(anInt);
    }
}
