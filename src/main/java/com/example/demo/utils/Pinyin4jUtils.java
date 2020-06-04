package com.example.demo.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 文字转拼音工具类
 * @author zj
 * @date 2020/4/9
 */
public class Pinyin4jUtils {
    /**
     * 将文字转为汉语拼音,全拼且首字母大写(蔡徐坤=>CaiXuKun)
     *
     * @param ChineseLanguage 要转换的文字
     * @return String
     */
    public static String getPinyinStringWithFirstUpper(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音
                    pinyin.append(toUpperCase4Index(PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0]));
                } else {// 如果字符不是中文,则不转换
                    pinyin.append(cl_chars[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return pinyin.toString();
    }


    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        char[] methodName = string.toCharArray();
        methodName[0] = toUpperCase(methodName[0]);
        return String.valueOf(methodName);
    }

    /**
     * 字符转成大写
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
    }
    public static void main(String[] args) {
        System.out.println("getPinyinString-->" + getPinyinStringWithFirstUpper("蔡徐坤"));
        System.out.println("getPinyinString-->" + getPinyinStringWithFirstUpper("参会人员"));
        System.out.println("getPinyinString-->" + getPinyinStringWithFirstUpper("会议类型"));
    }
}
