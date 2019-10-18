package com.madder.attendance.common;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符串创建
 * @Author wangqian
 * @Date 2019-09-18 9:52
 **/

public class RandomGenerate {

    public RandomGenerate() {
    }

    public static String getRandomChars(int charsLength, String randomChars) {
        if (StringUtils.isBlank(randomChars)) {
            randomChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }

        int codesLen = randomChars.length();
        StringBuilder verifyCode = new StringBuilder(charsLength);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for(int i = 0; i < charsLength; ++i) {
            verifyCode.append(randomChars.charAt(random.nextInt(codesLen)));
        }

        return verifyCode.toString();
    }

    public static String getRandomChars(int charsLength) {
        return getRandomChars(charsLength, "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
}
