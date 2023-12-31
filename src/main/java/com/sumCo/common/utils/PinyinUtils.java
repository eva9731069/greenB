package com.sumCo.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author oplus
 * @Description: TODO(中文轉拼音工具)
 * @date 2017-9-26 10:09
 */
public class PinyinUtils {

    /**
     * 將文字轉為漢語拼音
     * @param ChineseLanguage 要轉成拼音的中文
     */
    private static String toHanyuPinyin(String ChineseLanguage, HanyuPinyinCaseType caseType){
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
        try {
            for (int i=0; i<cl_chars.length; i++){
                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")){
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                } else {
                    hanyupinyin += cl_chars[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return hanyupinyin;
    }

    /**
     * 將文字轉為大寫漢語拼音
     * @param ChineseLanguage 要轉成拼音的中文
     * @return
     */
    public static String toHanyuPinyinUpperCase(String ChineseLanguage){
        return toHanyuPinyin(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 將文字轉為小寫漢語拼音
     * @param ChineseLanguage 要轉成拼音的中文
     * @return
     */
    public static String toHanyuPinyinLowerCase(String ChineseLanguage){
        return toHanyuPinyin(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 取第一個漢字的第一個字符
     * @param ChineseLanguage 要轉成拼音的中文
     * @param caseType 大寫還是小寫
     * @return
     */
    private static String getFirstLetter(String ChineseLanguage, HanyuPinyinCaseType caseType){
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[\u4e00-\u9fa5]+")) {
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(cl_chars[0], defaultFormat)[0].substring(0, 1);
            } else {
                hanyupinyin += cl_chars[0];
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return hanyupinyin;
    }

    /**
     * 取第一個漢字的第一個大寫字符
     * @param ChineseLanguage 要轉成拼音的中文
     * @return
     */
    public static String getFirstLetterUpperCase(String ChineseLanguage){
        return getFirstLetter(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 取第一個漢字的第一個小寫字符
     * @param ChineseLanguage 要轉成拼音的中文
     * @return
     */
    public static String getFirstLetterLowerCase(String ChineseLanguage){
        return getFirstLetter(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

}
