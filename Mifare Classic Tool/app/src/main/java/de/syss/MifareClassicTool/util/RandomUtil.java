package de.syss.MifareClassicTool.util;

import java.util.Random;

/**
 * <p>文件描述：<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/2<p>
 * <p>更新时间：2024/2/2<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
public class RandomUtil {
    public static String generateRandomChinese(int count) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            int startIndex = 0x4E00; // Unicode编码表示汉字的起始索引

            Random random = new Random();
            int index = random.nextInt((0x9FA5 - startIndex) / 2) * 2 + startIndex; // 每两个连续的Unicode编码表示一个汉字

            char c = (char) index;
            sb.append(c);
        }

        return sb.toString();
    }

    public static String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }

        return sb.toString();
    }

}
