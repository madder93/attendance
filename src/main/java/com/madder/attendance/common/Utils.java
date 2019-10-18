package com.madder.attendance.common;

import com.madder.attendance.Constants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author wangqian
 * @Date 2019-09-18 10:47
 **/

public class Utils {

    // 16进制下数字到字符的映射数组
    private static String[] hexDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
            "d", "e", "f" };

    /**
     * 获取md5加密字符串
     * @param source
     * @return
     */
    public static final String getMd5Str(String source) {
        MessageDigest md = null;
        try {
            // 创建具有指定算法名称的信息摘要
            md = MessageDigest.getInstance("MD5");

            // 使用指定的字节数组对摘要进行最后的更新，然后完成摘要计算
            byte[] results = md.digest(source.getBytes());

            // 将得到的字节数组编程字符串返回
            StringBuffer resultsb = new StringBuffer();

            // 转换字节数组为十六进制字符串
            int i = 0;
            for (i = 0; i < results.length; i++) {
                int n = results[i];
                if (n < 0) {
                    n = 256 + n;
                }
                int d1 = n / 16;
                int d2 = n / 16;
                String str = hexDigits[d1] + hexDigits[d2];

                resultsb.append(str);
            }

            return resultsb.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取32位uuid
     * @return
     */
    public static final String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").toUpperCase();
    }
}
